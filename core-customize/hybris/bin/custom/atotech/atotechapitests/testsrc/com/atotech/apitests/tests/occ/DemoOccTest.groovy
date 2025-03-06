package com.atotech.apitests.tests.occ

import com.atotech.apitests.api.occ.dto.AuthorizationDto
import com.atotech.apitests.api.occ.dto.CustomerDto
import com.atotech.apitests.api.occ.request.AuthorizationRequest
import com.atotech.apitests.api.occ.request.CustomerRequest
import com.atotech.apitests.api.occ.testdata.AuthorizationTestData
import com.atotech.apitests.api.occ.testdata.CustomerTestData
import com.atotech.apitests.tests.AbstractApiTest
import de.hybris.bootstrap.annotations.ManualTest

@ManualTest
class DemoOccTest extends AbstractApiTest {

    def "Correct current user info"() {
        given: "authorized customer"
        def adminAuthorization = AuthorizationDto.builder
                .username(AuthorizationTestData.OrganizationAdministrator.USERNAME)
                .pwd(AuthorizationTestData.OrganizationAdministrator.PWD)
                .build()
        AuthorizationRequest.authorizeCustomer(restClient, adminAuthorization)

        when: "customer get his info"
        def actualCustomerInfo = CustomerRequest.getCurrentCustomerInfo(restClient)

        then: "info is correct"
        def expectedCustomerInfo = CustomerDto.builder()
            .name(CustomerTestData.CustomerAdmin.NAME)
            .uid(CustomerTestData.CustomerAdmin.UID)
            .firstName(CustomerTestData.CustomerAdmin.FIRSTNAME)
            .lastName(CustomerTestData.CustomerAdmin.LASTNAME)
            .build()

        assert actualCustomerInfo == expectedCustomerInfo
    }
}
