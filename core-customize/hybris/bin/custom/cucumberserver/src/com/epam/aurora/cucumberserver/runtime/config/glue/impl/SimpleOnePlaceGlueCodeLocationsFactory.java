package com.epam.aurora.cucumberserver.runtime.config.glue.impl;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import com.epam.aurora.cucumberserver.runtime.config.glue.GlueCodeLocationsFactory;
import de.hybris.platform.servicelayer.config.ConfigurationService;

public class SimpleOnePlaceGlueCodeLocationsFactory implements GlueCodeLocationsFactory {
    private ConfigurationService configurationService;
    private String gluePropertyName;

    public SimpleOnePlaceGlueCodeLocationsFactory(ConfigurationService configurationService, String gluePropertyName) {
        this.configurationService = configurationService;
        this.gluePropertyName = gluePropertyName;
    }

    @Override
    public List<String> get() {
        return Collections.singletonList(MessageFormat.format(GLUE_FROM_CLASSPATH_TEMPLATE, configurationService.getConfiguration().getString(gluePropertyName)));
    }
}
