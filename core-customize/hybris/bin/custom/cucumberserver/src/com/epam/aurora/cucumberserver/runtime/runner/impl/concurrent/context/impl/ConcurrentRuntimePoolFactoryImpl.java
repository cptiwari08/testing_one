package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.impl;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.factory.CucumberRuntimeFactory;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentRuntimePool;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentRuntimePoolFactory;
import de.hybris.platform.servicelayer.config.ConfigurationService;

public class ConcurrentRuntimePoolFactoryImpl implements ConcurrentRuntimePoolFactory {
    private CucumberRuntimeFactory cucumberRuntimeFactory;
    private String shouldUseRealRuntimesPoolPropertyName;
    private String realRuntimePoolCapacityPropertyName;
    private ConfigurationService configurationService;

    public ConcurrentRuntimePoolFactoryImpl(CucumberRuntimeFactory cucumberRuntimeFactory, String shouldUseRealRuntimesPoolPropertyName,
            String realRuntimePoolCapacityPropertyName, ConfigurationService configurationService) {
        this.cucumberRuntimeFactory = cucumberRuntimeFactory;
        this.shouldUseRealRuntimesPoolPropertyName = shouldUseRealRuntimesPoolPropertyName;
        this.realRuntimePoolCapacityPropertyName = realRuntimePoolCapacityPropertyName;
        this.configurationService = configurationService;
    }

    @Override
    public ConcurrentRuntimePool create(CucumberRuntimeOptions cucumberRuntimeOptions) {
        if (configurationService.getConfiguration().getBoolean(shouldUseRealRuntimesPoolPropertyName)) {
            int pullCapacity = configurationService.getConfiguration().getInt(realRuntimePoolCapacityPropertyName);
            return new RealConcurrentRuntimePoolImpl(cucumberRuntimeOptions, cucumberRuntimeFactory, pullCapacity);
        }
        
        return new SimpleConcurrentRuntimePoolImpl(cucumberRuntimeOptions, cucumberRuntimeFactory);
    }
}
