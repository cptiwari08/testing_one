package com.epam.aurora.cucumberserver.runtime.runner.impl;

import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeRunner;
import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeRunnerFactory;
import de.hybris.platform.servicelayer.config.ConfigurationService;

public class CucumberRuntimeRunnerFactoryImpl implements CucumberRuntimeRunnerFactory {
    private String shouldUseConcurrentRunnerPropertyName;
    private ConfigurationService configurationService;

    private CucumberRuntimeRunner cucumberRuntimeRunner;
    private CucumberRuntimeRunner cucumberConcurrentRuntimeRunner;

    public CucumberRuntimeRunnerFactoryImpl(String shouldUseConcurrentRunnerPropertyName, ConfigurationService configurationService,
            CucumberRuntimeRunner cucumberRuntimeRunner, CucumberRuntimeRunner cucumberConcurrentRuntimeRunner) {
        this.shouldUseConcurrentRunnerPropertyName = shouldUseConcurrentRunnerPropertyName;
        this.configurationService = configurationService;
        this.cucumberRuntimeRunner = cucumberRuntimeRunner;
        this.cucumberConcurrentRuntimeRunner = cucumberConcurrentRuntimeRunner;
    }

    @Override
    public CucumberRuntimeRunner get() {
        if (configurationService.getConfiguration().getBoolean(shouldUseConcurrentRunnerPropertyName)) {
            return cucumberConcurrentRuntimeRunner;
        }

        return cucumberRuntimeRunner;
    }
}
