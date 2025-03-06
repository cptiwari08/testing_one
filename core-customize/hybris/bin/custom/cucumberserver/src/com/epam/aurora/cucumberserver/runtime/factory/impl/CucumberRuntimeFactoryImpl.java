package com.epam.aurora.cucumberserver.runtime.factory.impl;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.config.collector.CliArgumentsCollector;
import com.epam.aurora.cucumberserver.runtime.factory.CucumberRuntimeFactory;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;

public class CucumberRuntimeFactoryImpl implements CucumberRuntimeFactory {
    private CliArgumentsCollector cliArgumentsCollector;

    public CucumberRuntimeFactoryImpl(CliArgumentsCollector cliArgumentsCollector) {
        this.cliArgumentsCollector = cliArgumentsCollector;
    }

    @Override
    public Runtime create(CucumberRuntimeOptions options) {
        return new Runtime(options.getResourceLoaderOptions().getResourceLoader(), options.getClassLoader(), options.getExecutionBackends(),
                generateCliArguments(options));
    }

    private RuntimeOptions generateCliArguments(CucumberRuntimeOptions options) {
        return new RuntimeOptions(cliArgumentsCollector.collect(options));
    }
}
