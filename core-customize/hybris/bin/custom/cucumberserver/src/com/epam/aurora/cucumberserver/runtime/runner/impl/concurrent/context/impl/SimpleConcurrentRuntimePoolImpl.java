package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.impl;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.factory.CucumberRuntimeFactory;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentRuntimePool;
import cucumber.runtime.Runtime;

public class SimpleConcurrentRuntimePoolImpl implements ConcurrentRuntimePool {
    private CucumberRuntimeOptions cucumberRuntimeOptions;
    private CucumberRuntimeFactory cucumberRuntimeFactory;

    public SimpleConcurrentRuntimePoolImpl(CucumberRuntimeOptions cucumberRuntimeOptions, CucumberRuntimeFactory cucumberRuntimeFactory) {
        this.cucumberRuntimeOptions = cucumberRuntimeOptions;
        this.cucumberRuntimeFactory = cucumberRuntimeFactory;
    }

    @Override
    public Runtime pullRuntime() {
        return cucumberRuntimeFactory.create(cucumberRuntimeOptions);
    }

    @Override
    public void returnRuntime(Runtime runtime) {

    }
}
