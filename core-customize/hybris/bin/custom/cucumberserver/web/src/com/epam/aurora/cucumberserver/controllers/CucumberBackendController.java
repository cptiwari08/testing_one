package com.epam.aurora.cucumberserver.controllers;

import javax.annotation.Resource;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import com.epam.aurora.cucumberserver.runtime.facade.CucumberRuntimeFacade;

@RestController
public class CucumberBackendController {
    @Resource
    private CucumberRuntimeFacade cucumberRuntimeFacade;

    @RequestMapping(value = "/runbackendcucumbertests", method = RequestMethod.GET)
    public String runFeaturesFromFileSystem(@RequestParam MultiValueMap<String, String> requestParams) {
        return cucumberRuntimeFacade.runFeaturesFromFileSystem(requestParams);
    }

    @RequestMapping(value = "/runadhocfeature", method = RequestMethod.POST)
    public TestExecutionResult runAdhocFeature(@RequestBody String featureText) {
        return cucumberRuntimeFacade.runAdhocFeature(featureText);
    }

}
