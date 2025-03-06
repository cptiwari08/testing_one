package com.epam.aurora.cucumberserver.runtime.config.resourceloader;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Objects;

import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;

public class MultiCucumberResourceLoaderOptionsSource implements CucumberResourceLoaderOptionsSource {

    private String featurePath;
    private ClassLoader classLoader;

    public MultiCucumberResourceLoaderOptionsSource(String featurePath, ClassLoader classLoader) {
        this.featurePath = Objects.requireNonNull(featurePath);
        this.classLoader = Objects.requireNonNull(classLoader);
    }

    @Override
    public List<String> getOptions() {
        return asList(featurePath);
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return new MultiLoader(classLoader);
    }

}
