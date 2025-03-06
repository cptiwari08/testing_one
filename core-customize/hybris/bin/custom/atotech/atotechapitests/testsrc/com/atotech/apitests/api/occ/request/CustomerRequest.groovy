package com.atotech.apitests.api.occ.request

import com.atotech.apitests.api.common.request.BaseRequest
import com.atotech.apitests.api.occ.dto.CustomerDto
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC
import static org.apache.http.HttpStatus.SC_OK

class CustomerRequest extends BaseRequest {

    static CustomerDto getCurrentCustomerInfo(RESTClient restClient) {
        HttpResponseDecorator response = restClient.get(
                path: getOccBasePathWithSite() + '/orgUsers/current',
                contentType: JSON,
                requestContentType: URLENC)

        assert response.status == SC_OK

        return new CustomerDto(response.data)
    }


    protected static String getOccBasePathWithSite() {
        CONFIGS.OCC_BASE_PATH_WITH_SITE
    }

}
