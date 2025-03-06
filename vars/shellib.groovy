#!/usr/bin/env groovy
import groovy.transform.Field
import groovy.json.JsonSlurper
import groovy.json.JsonSlurperClassic

@Field DB_NAME = 'hybris'
@Field MYSQL_VOLUME = 'mysql-vol'
@Field DB_FILE = 'db.properties'
@Field PREFIX = '/opt/core-customize'
@Field CFG_FILE = "${PREFIX}/hybris/config/local.properties"
@Field DATA_LOG = "${PREFIX}/hybris/test-reports/data_update.log"
@Field WAIT_FILE = "${PREFIX}/hybris/bin/custom/atotech/atotechbackendfeatures/src/com/atotech/backendfeatures/utils/AwaitingUtils.java"
@Field LOCAL_DB = "${JENKINS_HOME}/jenkins.db"
@Field MR_TABLE = "MR"


// Return var PREFIX
String getPrefix() {
    return "${PREFIX}"
}

def add_prop() {
    withCredentials([string(credentialsId: 'client_password', variable: 'cl_password')]) {
        sh """
            echo '' >> ${CFG_FILE}
            echo build.parallel=true >> ${CFG_FILE}
            echo cpi.customer.odata.client.default.password=${cl_password} >> ${CFG_FILE}
            echo cpi.product.odata.client.default.password=${cl_password} >> ${CFG_FILE}
            echo order.export.destination.password=${cl_password} >> ${CFG_FILE}
            echo smartedit.smartedittools.config=no >> ${CFG_FILE}
            echo cmssmartedit.smartedittools.config=false >> ${CFG_FILE}
            echo atotechsmartedit.smartedittools.config=false >> ${CFG_FILE}
            echo aurora.cucumberserver.concurrent.should.run.concurrently=false >> ${CFG_FILE}
            sed -ie 's/private static final long TIMEOUT = 50/private static final long TIMEOUT = 300/' ${WAIT_FILE}
        """
    }
}

// Create mysql instance for hybris
def mysql(Boolean init, String mysql_name) {
    "init=$init, mysql_name=$mysql_name"

    withCredentials([string(credentialsId: 'DB_USER', variable: 'db_user'),
                     string(credentialsId: 'DB_PASSWORD', variable: 'db_password')]) {
        switch ("${JOB_BASE_NAME}") {
            case "hybris-init":
                sh """
                    docker volume rm ${MYSQL_VOLUME} || true
                    docker run --name ${mysql_name} -d -v ${MYSQL_VOLUME}:/var/lib/mysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_DATABASE=${DB_NAME} -e MYSQL_USER=${db_user} -e MYSQL_PASSWORD=${db_password} mysql:5.7
                """    
                break
            case "hybris-tests":
                if (init) {
                    sh "docker run --name ${mysql_name} -d -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_DATABASE=${DB_NAME} -e MYSQL_USER=${db_user} -e MYSQL_PASSWORD=${db_password} mysql:5.7"
                }
                else {
                    sh """
                        docker volume create ${mysql_name}-vol
                        docker run --rm -v ${MYSQL_VOLUME}:/mnt/mysql-vol:ro -v ${mysql_name}-vol:/mnt/new-mysql-vol alpine ash -c "cd /mnt/mysql-vol; cp -a . /mnt/new-mysql-vol"
                        docker run --name ${mysql_name} -d -v ${mysql_name}-vol:/var/lib/mysql mysql:5.7
                    """
                }
                break
            default:
                error('Call from inadmissible job!')
        }

        sh """
            echo > ${DB_FILE}
            echo db.url=jdbc:mysql://\$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' ${mysql_name})/${DB_NAME}?useSSL=false\\&characterEncoding=utf8 >> ${DB_FILE}
            echo db.driver=com.mysql.jdbc.Driver >> ${DB_FILE}
            echo db.username=${db_user} >> ${DB_FILE}
            echo db.password=${db_password} >> ${DB_FILE}
            echo mysql.allow.fractional.seconds=true >> ${DB_FILE}
        """ 
    }  
}

// Hybris build func
def build() {
    sh """
        cp -rp core-customize/hybris/* ${PREFIX}/hybris/
        cp -rp core-customize/bootstrap/ ${PREFIX}/
        echo '' >> ${CFG_FILE}
        [ -f ${DB_FILE} ] && cat ${DB_FILE} >> ${CFG_FILE} || exit 1
    """
    add_prop()
    sh """ 
        cd ${PREFIX}/hybris/bin/platform
        . ./setantenv.sh
        ant clean all
    """
}

// Hybris init/update func
def data(String action) {
    sh """
        cd ${PREFIX}/hybris/bin/platform
        . ./setantenv.sh
        ant ${action} 2>&1 | tee ${DATA_LOG}
        if (grep 'BUILD FAILED' ${DATA_LOG}); then exit 10; fi
    """
    withCredentials([string(credentialsId: 'DB_USER', variable: 'db_user'),
                     string(credentialsId: 'DB_PASSWORD', variable: 'db_password')]) {
        sh """
            (grep '\\sERROR\\s' ${DATA_LOG} | grep -v DefaultKieModuleService) || true
            db_host=\$(grep db.url ${CFG_FILE} | egrep -o '[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+')
            impex_file=\$(grep '\\sERROR\\s' ${DATA_LOG} | egrep -o '\\([0-9A-Z]+)|\\([0-9A-Z]+-ImpEx-Import)' | sed -r 's/(\\(|\\))//g')
    
            for el in \${impex_file}; do
                sql_expr="SELECT t1.p_location FROM cronjobs t0 JOIN medias t1 ON t1.OwnerPkString=t0.PK WHERE t1.p_mime='application/zip' AND t0.p_code='\${el}'"
                file_path=\$(mysql -h \${db_host} -D ${DB_NAME} -u ${db_user} -e "\${sql_expr}" --password=${db_password} -N -s 2>/dev/null)
                echo === \${el} ===
                zcat ${PREFIX}/hybris/data/media/sys_master/\${file_path}
            done
        """
    }
}

// Add sonar properties to ${CFG_FILE}
def sonar(String sonar_host, String sonar_ext, String branch) {
    "sonar_host=$sonar_host, sonar_ext=$sonar_ext, branch=$branch"
    withCredentials([string(credentialsId: 'sonar_login', variable: 'sonar_login')]) {
        sh """
            mkdir ${PREFIX}/hybris/test-reports/sonar
            echo '' >> ${CFG_FILE}
            echo sonar.language=java >> ${CFG_FILE}
            echo sonar.projectName=${branch.replaceAll("[^a-zA-Z0-9-]", "_")} >> ${CFG_FILE}
            echo sonar.projectKey=${branch.replaceAll("[^a-zA-Z0-9-]", "_")} >> ${CFG_FILE}
            echo sonar.extensions=${sonar_ext} >> ${CFG_FILE}
            echo sonar.host.url=http://${sonar_host}:9000 >> ${CFG_FILE}
            echo sonar.login=${sonar_login} >> ${CFG_FILE}
            echo sonar.working.directory=${PREFIX}/hybris/test-reports/sonar >> ${CFG_FILE}
        """
    }
}

def getJobStatus(String jenkins_host, String jobName){
    "jenkins_host=$jenkins_host, jobName=$jobName"
    def request = httpRequest authentication: 'api-user', url: "http://${jenkins_host}:8080/job/${jobName}/lastBuild/api/json"
    def requestJson = new JsonSlurper().parseText(request.getContent())
    return requestJson['result']
}

def postMessage(String jobName, String buildNum, String buildUrl, String status, String facts) {
    "jobName=$jobName, buildNum=$buildNum, buildUrl=$buildUrl, status=$status, facts=$facts"
    
    switch ("${status}") {
        case 'SUCCESS': color = '00ee00'; break
        case 'FAILURE': color = 'ee0000'; break
        case 'UNSTABLE': color = 'eeee00'; break
        default: color = '0000ee'
    }

    def body = """{
        "@type": "MessageCard",
        "themeColor": "${color}",
    	"summary": "Notification from ${jobName}",
    	"sections": [{
    		"activityTitle": "Notification from ${jobName} #${buildNum}",
    		"activitySubtitle": "${status}",
    		"activityImage": "https://www.jenkins.io/images/logos/balloon/balloon.png",
    		"facts": [${facts}]
        }],
        "potentialAction": [{
            "@type": "OpenUri",
            "name": "View Build",
            "targets": [{"os": "default", "uri": "${buildUrl}"}] 
        }]
    }"""

    try { httpRequest httpMode: 'POST', contentType: 'APPLICATION_JSON', requestBody: body, url: "${TeamsWebHook}" }
    catch (Exc) { print 'Message not sent' }
}

def getCommit(String mr_id) {
    sql_expr = "SELECT last_commit FROM ${MR_TABLE} where mr_iid=${mr_id}"
    return sh(script: "sqlite3 ${LOCAL_DB} '${sql_expr}'", returnStdout: true).trim()
}

def updateMR(String branch, String project_id, String status) {
    "branch=$branch, project_id=$project_id, status=$status"
    mr_opened = "https://gitlab.com/api/v4/projects/${project_id}/merge_requests?state=opened"
    withCredentials([string(credentialsId: 'atotech-gitlab-api', variable: 'api_token')]) {
        mr = httpRequest httpMode: 'GET', url: "${mr_opened}", customHeaders: [[name: 'Authorization', value: "Bearer ${api_token}"]]
        mrJson = new JsonSlurperClassic().parseText(mr.getContent())
        mrJson.each {
            if (it['source_branch'] == "${branch}") { 
                if("${status}" == 'SUCCESS') { label = 'CI_PASSED' } else { label = 'CI_FAILED' }
                sql_expr = "INSERT into ${MR_TABLE}(mr_iid,last_commit,status) VALUES(${it['iid']},\"${it['sha']}\",\"${label}\") ON CONFLICT(mr_iid) DO UPDATE SET last_commit=excluded.last_commit,status=excluded.status"
                sh "sqlite3 ${LOCAL_DB} '${sql_expr}'"
                build_link = currentBuild.getUpstreamBuilds()[0].getAbsoluteUrl()
                mr_note = "https://gitlab.com/api/v4/projects/${project_id}/merge_requests/${it['iid']}/notes?body=**${label}**%20[[View_Build]](${build_link})"
                httpRequest httpMode: 'POST', url: "${mr_note}", customHeaders: [[name: 'Authorization', value: "Bearer ${api_token}"]]
            }
        }
    }
}