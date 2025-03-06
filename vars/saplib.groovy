#!/usr/bin/env groovy
import groovy.transform.Field
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.json.JsonSlurperClassic

@Field TOKEN_ID = 'ccv2-token'
@Field ccv2_url = 'https://portalrotapi.hana.ondemand.com/v2/subscriptions/e7b568920515449797ce03df007f847c'


def getBuildName(String buildCode) {
    withCredentials([string(credentialsId: "${TOKEN_ID}", variable: 'token')]) {
        def response = httpRequest httpMode: 'GET', url: "${ccv2_url}/builds/${buildCode}", customHeaders: [[name: 'Authorization', value: "Bearer ${token}"]]
        def responseJson = new JsonSlurper().parseText(response.getContent())
        return responseJson['name']
    }
}

def getLastBuild(String branch, String param) {
    "branch=$branch, param=$param"
    def build = "${branch}"+".0"
    withCredentials([string(credentialsId: "${TOKEN_ID}", variable: 'token')]) {
        last_build = '\$orderby=buildStartTimestamp%20desc&\$top=24'
        def response = httpRequest httpMode: 'GET', url: "${ccv2_url}/builds?${last_build}", customHeaders: [[name: 'Authorization', value: "Bearer ${token}"]]
        def responseJson = new JsonSlurper().parseText(response.getContent())
        responseJson.value.find { if (it.name ==~ /${branch}\.\d{1,3}/) {
            if (param == 'name') { build = it.name; return true }
            if (param == 'hash') { build = it.properties.find{it.key == 'project.repository.revision'}.value; return true }
        }}
        return build
    }
}

def getJobStatus(String job_type, String job_code) {
    "job_type=$job_type, job_code=$job_code"
    withCredentials([string(credentialsId: "${TOKEN_ID}", variable: 'token')]) {
        def response = httpRequest httpMode: 'GET', url: "${ccv2_url}/${job_type}/${job_code}", customHeaders: [[name: 'Authorization', value: "Bearer ${token}"]]
        def responseJson = new JsonSlurper().parseText(response.getContent())
        return responseJson['status']
    }
}

def getCommits() {
    commits = []
    deployments_url = "deployments?environmentCode=d1&\$orderby=deployedTimestamp%20desc&\$top=2"
    withCredentials([string(credentialsId: "${TOKEN_ID}", variable: 'token')]) {
        def deployments = httpRequest httpMode: 'GET', url: "${ccv2_url}/${deployments_url}", customHeaders: [[name: 'Authorization', value: "Bearer ${token}"]]
        def deploymentsJson = new JsonSlurperClassic().parseText(deployments.getContent())
        def builds = httpRequest httpMode: 'GET', url: "${ccv2_url}/builds?\$top=10", customHeaders: [[name: 'Authorization', value: "Bearer ${token}"]]
        def buildsJson = new JsonSlurperClassic().parseText(builds.getContent())
        commits.add(buildsJson.value.find{it.code == "${deploymentsJson['value'][0]['buildCode']}"}.properties.find{it.key == 'project.repository.revision'}.value)
        commits.add(buildsJson.value.find{it.code == "${deploymentsJson['value'][1]['buildCode']}"}.properties.find{it.key == 'project.repository.revision'}.value)
        commits.add(buildsJson.value.find{it.code == "${deploymentsJson['value'][0]['buildCode']}"}.name)
        commits.add(buildsJson.value.find{it.code == "${deploymentsJson['value'][1]['buildCode']}"}.name)
    }
    return commits
}

def createBuild(String branch, String tag) {
    "branch=$branch, tag=$tag"
    def build_num = ((getLastBuild("${branch}", 'name') =~ "(\\d+\$)")[0][1]).toInteger() + 1
    def body = [ "branch": "${tag}", "name": "${branch}"+"."+"${build_num}" ]
    def bodyJson = JsonOutput.toJson(body)
    withCredentials([string(credentialsId: "${TOKEN_ID}", variable: 'token')]) {
        def response = httpRequest httpMode: 'POST', contentType: 'APPLICATION_JSON', requestBody: bodyJson, url: "${ccv2_url}/builds", customHeaders: [[name: 'Authorization', value: "Bearer ${token}"]]
        def responseJson = new JsonSlurper().parseText(response.getContent())
        return responseJson['code']
    }
}

def createDeployment(String build_code, String env, String db_mode, String strategy) {
    "build_code=$build_code, env=$env, db_mode=$db_mode, strategy=$strategy"
    def body = [ "buildCode": "${build_code}", "databaseUpdateMode": "${db_mode}", "environmentCode": "${env}", "strategy": "${strategy}" ]
    def bodyJson = JsonOutput.toJson(body)
    withCredentials([string(credentialsId: "${TOKEN_ID}", variable: 'token')]) {
        def response = httpRequest httpMode: 'POST', contentType: 'APPLICATION_JSON', requestBody: bodyJson, url: "${ccv2_url}/deployments", customHeaders: [[name: 'Authorization', value: "Bearer ${token}"]]
        def responseJson = new JsonSlurper().parseText(response.getContent())
        return responseJson['code']
    }
}
