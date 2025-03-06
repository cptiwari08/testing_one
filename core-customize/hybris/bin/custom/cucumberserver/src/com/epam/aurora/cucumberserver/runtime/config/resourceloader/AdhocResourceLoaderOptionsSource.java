package com.epam.aurora.cucumberserver.runtime.config.resourceloader;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cucumber.runtime.io.Resource;
import cucumber.runtime.io.ResourceLoader;

public class AdhocResourceLoaderOptionsSource implements CucumberResourceLoaderOptionsSource {

    private List<Resource> resources;

    public AdhocResourceLoaderOptionsSource(List<Resource> resources) {
        this.resources = Objects.requireNonNull(resources);
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("adhocfile");
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return ($, $$) -> resources;
    }

}
