package com.epam.aurora.cucumberserver.runtime.config.resourceloader;

import java.util.Collections;
import java.util.List;

import cucumber.runtime.io.ResourceLoader;

public class NoOpCucumberResourceOptionsSource implements CucumberResourceLoaderOptionsSource {

    public static final CucumberResourceLoaderOptionsSource NO_OP_INSTANCE = new NoOpCucumberResourceOptionsSource();

    @Override
    public List<String> getOptions() {
        return Collections.emptyList();
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return ($, $$) -> Collections.emptyList();
    }

}
