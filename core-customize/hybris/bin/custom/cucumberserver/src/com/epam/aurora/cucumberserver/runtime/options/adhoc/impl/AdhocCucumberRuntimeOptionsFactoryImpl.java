package com.epam.aurora.cucumberserver.runtime.options.adhoc.impl;

import java.util.Collections;

import com.epam.aurora.cucumberserver.runtime.options.adhoc.AdhocCucumberRuntimeOptionsFactory;
import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.config.glue.GlueCodeLocationsFactory;
import com.epam.aurora.cucumberserver.runtime.config.resourceloader.AdhocResourceLoaderOptionsSource;
import com.epam.aurora.cucumberserver.runtime.config.resourceloader.resource.InMemoryCucumberResource;

public class AdhocCucumberRuntimeOptionsFactoryImpl implements AdhocCucumberRuntimeOptionsFactory {
    private static final String NAME_OF_IN_MEMORY_CUCUMBER_RESOURCE = "adhoc feature";

    private GlueCodeLocationsFactory glueCodeLocationsFactory;

    public AdhocCucumberRuntimeOptionsFactoryImpl(GlueCodeLocationsFactory glueCodeLocationsFactory) {
        this.glueCodeLocationsFactory = glueCodeLocationsFactory;
    }

    @Override
    public CucumberRuntimeOptions create(String featureText) {
        CucumberRuntimeOptions options = new CucumberRuntimeOptions();

        options.setGlueCode(glueCodeLocationsFactory.get());
        options.setResourceLoaderOptions(createAdhocResourceLoaderOptionsSource(featureText));

        return options;
    }

    private AdhocResourceLoaderOptionsSource createAdhocResourceLoaderOptionsSource(String featureText) {
        return new AdhocResourceLoaderOptionsSource(Collections.singletonList(createInMemoryCucumberResource(featureText)));
    }

    private InMemoryCucumberResource createInMemoryCucumberResource(String featureText) {
        return new InMemoryCucumberResource(NAME_OF_IN_MEMORY_CUCUMBER_RESOURCE, featureText);
    }
}
