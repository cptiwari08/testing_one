package com.epam.aurora.cucumberserver.runtime.config.resourceloader;

import com.epam.aurora.cucumberserver.runtime.config.CucumberOptionsSource;

import cucumber.runtime.io.ResourceLoader;

public interface CucumberResourceLoaderOptionsSource extends CucumberOptionsSource {

    ResourceLoader getResourceLoader();

}
