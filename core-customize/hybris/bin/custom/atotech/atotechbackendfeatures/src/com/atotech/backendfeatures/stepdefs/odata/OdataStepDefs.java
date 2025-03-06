package com.atotech.backendfeatures.stepdefs.odata;

import com.atotech.backendfeatures.helpers.api.odata.OdataApiHelper;
import cucumber.api.java.en.Given;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class OdataStepDefs {

    @Resource
    private OdataApiHelper odataApiHelper;

    @Given("^\"([^\"]*)\" object is sent to odata using \"([^\"]*)\" body template")
    public void sendObjectToOdata(String objectCode, String template, Map<String, String> values) {
        Response response = odataApiHelper.postIntegrationObject(objectCode.toLowerCase(), template, values);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
