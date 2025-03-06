package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.impl;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.config.collector.CliArgumentsCollector;
import com.epam.aurora.cucumberserver.runtime.factory.CucumberRuntimeFactory;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContext;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContextFactory;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentRuntimePoolFactory;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.executor.factory.CucumberExecutorServiceFactory;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;

public class ConcurrentCucumberContextFactoryImpl implements ConcurrentCucumberContextFactory {
    private CliArgumentsCollector cliArgumentsCollector;
    private CucumberRuntimeFactory cucumberRuntimeFactory;
    private ConcurrentRuntimePoolFactory concurrentRuntimePoolFactory;
    private CucumberExecutorServiceFactory cucumberExecutorServiceFactory;

    public ConcurrentCucumberContextFactoryImpl(CliArgumentsCollector cliArgumentsCollector, CucumberRuntimeFactory cucumberRuntimeFactory,
            ConcurrentRuntimePoolFactory concurrentRuntimePoolFactory, CucumberExecutorServiceFactory cucumberExecutorServiceFactory) {
        this.cliArgumentsCollector = cliArgumentsCollector;
        this.cucumberRuntimeFactory = cucumberRuntimeFactory;
        this.concurrentRuntimePoolFactory = concurrentRuntimePoolFactory;
        this.cucumberExecutorServiceFactory = cucumberExecutorServiceFactory;
    }

    @Override
    public ConcurrentCucumberContext create(CucumberRuntimeOptions cucumberRuntimeOptions) {
        RuntimeOptions runtimeOptions = new RuntimeOptions(cliArgumentsCollector.collect(cucumberRuntimeOptions));
        Runtime runtime = cucumberRuntimeFactory.create(cucumberRuntimeOptions);

        Formatter formatter = runtimeOptions.formatter(cucumberRuntimeOptions.getClassLoader());
        Reporter reporter = runtimeOptions.reporter(cucumberRuntimeOptions.getClassLoader());

        return new ConcurrentCucumberContextImpl(cucumberRuntimeOptions, runtimeOptions, runtime, concurrentRuntimePoolFactory.create(cucumberRuntimeOptions),
                formatter, reporter, cucumberExecutorServiceFactory.create());
    }
}
