package com.epam.aurora.cucumberserver.runtime.facade;

import org.springframework.util.MultiValueMap;

import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;

public interface CucumberRuntimeFacade {
    String runFeaturesFromFileSystem(MultiValueMap<String, String> requestParams);

    TestExecutionResult runAdhocFeature(String featureText);
}
