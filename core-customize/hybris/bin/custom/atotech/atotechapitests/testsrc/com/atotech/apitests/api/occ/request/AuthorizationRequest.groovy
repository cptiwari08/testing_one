package com.atotech.apitests.api.occ.request

import com.atotech.apitests.api.common.request.BaseRequest
import com.atotech.apitests.api.occ.dto.AuthorizationDto
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC
import static org.apache.http.HttpStatus.SC_OK

class AuthorizationRequest extends BaseRequest {

    static void authorizeCustomer(RESTClient restClient, AuthorizationDto authorizationDto, boolean doAssert = true) {
        HttpResponseDecorator response = restClient.post(
                uri: getOAuth2TokenUri(),
                path: getOAuth2TokenPath(),
                body: [
                        'grant_type'   : 'password',
                        'client_id'    : getClientId(),
                        'client_secret': getClientSecret(),
                        'username'     : authorizationDto.username,
                        'password'     : authorizationDto.pwd
                ],
                contentType: JSON,
                requestContentType: URLENC)

        if (doAssert) {
                def data = response.data

                assert response.status == SC_OK
                assert data.token_type == 'bearer'
                assert data.access_token
                assert data.expires_in
                assert data.refresh_token
        }

        addAuthorization(restClient, response.data)
    }

    protected static void addAuthorization(RESTClient client, token) {
        client.getHeaders().put('Authorization', ' Bearer ' + token.access_token)
    }

    protected static String getOAuth2TokenUri() {
        CONFIGS.OAUTH2_TOKEN_ENDPOINT_URI
    }

    protected static String getOAuth2TokenPath() {
        CONFIGS.OAUTH2_TOKEN_ENDPOINT_PATH
    }

    protected static String getClientId() {
        CONFIGS.CLIENT_ID
    }

    protected static String getClientSecret() {
        CONFIGS.CLIENT_SECRET
    }
}
