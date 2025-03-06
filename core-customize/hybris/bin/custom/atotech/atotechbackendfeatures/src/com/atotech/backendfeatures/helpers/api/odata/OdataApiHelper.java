package com.atotech.backendfeatures.helpers.api.odata;

import com.atotech.backendfeatures.helpers.files.FilesHelper;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Map;

import static java.lang.String.format;

@Component
public class OdataApiHelper implements InitializingBean {

    private static final String BASIC_AUTH = "basic";

    private static final String HYBRIS_HOST = "cucumber.testing.hybris.host";
    private static final String HYBRIS_PORT = "cucumber.testing.hybris.secure.port";

    private static final String BASE_PATH = "testing.odata.web.base.path";

    private static final String OBJECT_WEB_PATH = "testing.odata.object.%s.web.path";
    private static final String OBJECT_AUTH = "testing.odata.object.%s.auth";
    private static final String OBJECT_BASIC_AUTH_USER = "testing.odata.object.%s.auth.basic.user";
    private static final String OBJECT_BASIC_AUTH_PWD = "testing.odata.object.%s.auth.basic.pwd";

    private static final String TEMPLATE_PATH = "/atotechbackendfeatures/odata/requests/%s_template.json";

    private RequestSpecification odataRequestSpecification;

    @Resource
    private ConfigurationService configurationService;
    @Resource
    private FilesHelper filesHelper;

    public Response postIntegrationObject(String objectCode, String template, Map<String, String> valueMap) {
        String templatePath = format(TEMPLATE_PATH, template);
        String body = filesHelper.readTemplateWithPlaceholdersReplacing(templatePath, valueMap);
        return prepareSpecForPost(objectCode, body).post();
    }

    @Override
    public void afterPropertiesSet() {
        odataRequestSpecification = new RequestSpecBuilder()
                .setBaseUri(getOdataBaseUri())
                .setContentType(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .build();
        odataRequestSpecification = RestAssured.given(odataRequestSpecification);
    }

    protected RequestSpecification prepareSpecForPost(String objectCode, String body) {
        RequestSpecification postSpec = odataRequestSpecification.basePath(getPathForObject(objectCode))
                .body(body);
        addAuth(objectCode, postSpec);
        return postSpec;
    }

    protected RequestSpecification addAuth(String objectCode, RequestSpecification requestSpecification) {
        String authType = getAuthType(objectCode);
        if (BASIC_AUTH.equals(authType)) {
            return addBasicAuth(objectCode, requestSpecification);
        } else {
            throw new UnsupportedOperationException("Auth type [" + authType + "] is not implemented");
        }
    }

    protected RequestSpecification addBasicAuth(String objectCode, RequestSpecification requestSpecification) {
        String user = configurationService.getConfiguration()
                .getString(format(OBJECT_BASIC_AUTH_USER, objectCode));
        String pwd = configurationService.getConfiguration()
                .getString(format(OBJECT_BASIC_AUTH_PWD, objectCode));

        return requestSpecification.auth().basic(user, pwd);
    }

    protected String getOdataBaseUri() {
        String host = configurationService.getConfiguration().getString(HYBRIS_HOST);
        String port = configurationService.getConfiguration().getString(HYBRIS_PORT);

        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .port(port)
                .toUriString();
    }

    protected String getOdataBasePath() {
        return configurationService.getConfiguration().getString(BASE_PATH);
    }

    protected String getPathForObject(String objectCode) {
        String key = format(OBJECT_WEB_PATH, objectCode);
        String objectPath = configurationService.getConfiguration().getString(key);

        return getOdataBasePath() + objectPath;
    }

    protected String getAuthType(String objectCode) {
        String key = format(OBJECT_AUTH, objectCode);
        return configurationService.getConfiguration().getString(key);
    }
}
