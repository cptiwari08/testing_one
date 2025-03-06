package com.atotech.apitests.tests

import com.atotech.apitests.api.common.config.ConfigFactory
import com.atotech.apitests.api.common.httpclient.SSLIssuesIgnoringHttpClientFactory
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpClient
import org.junit.Ignore
import spock.lang.Specification

@Ignore
class AbstractApiTest extends Specification {

    protected static final ConfigObject CONFIGS = ConfigFactory.getDefaultConfig()

    protected RESTClient restClient

    def setup() {
        restClient = createRestClient()
    }

    def cleanup() {
        restClient.shutdown()
    }

    protected RESTClient createRestClient(String uri = CONFIGS.DEFAULT_HTTPS_URI) {
        def restClient = new RESTClient(uri)

        HttpClient httpClient = SSLIssuesIgnoringHttpClientFactory.createHttpClient()
        restClient.setClient(httpClient)

        restClient.handler.failure = restClient.handler.success

        return restClient
    }
}
