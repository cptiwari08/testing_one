package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.impl;

import java.util.concurrent.ExecutorService;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContext;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentRuntimePool;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;

public class ConcurrentCucumberContextImpl implements ConcurrentCucumberContext {
    private CucumberRuntimeOptions cucumberRuntimeOptions;
    private RuntimeOptions runtimeOptions;
    private Runtime singleRuntime;
    private ConcurrentRuntimePool concurrentRuntimePool;
    private Formatter formatter;
    private Reporter reporter;
    private ExecutorService executorService;

    public ConcurrentCucumberContextImpl(CucumberRuntimeOptions cucumberRuntimeOptions, RuntimeOptions runtimeOptions, Runtime singleRuntime,
            ConcurrentRuntimePool concurrentRuntimePool, Formatter formatter, Reporter reporter, ExecutorService executorService) {
        this.cucumberRuntimeOptions = cucumberRuntimeOptions;
        this.runtimeOptions = runtimeOptions;
        this.singleRuntime = singleRuntime;
        this.concurrentRuntimePool = concurrentRuntimePool;
        this.formatter = formatter;
        this.reporter = reporter;
        this.executorService = executorService;
    }

    @Override
    public CucumberRuntimeOptions getCucumberRuntimeOptions() {
        return cucumberRuntimeOptions;
    }

    @Override
    public RuntimeOptions getRuntimeOptions() {
        return runtimeOptions;
    }

    @Override
    public Runtime getRuntime() {
        return singleRuntime;
    }

    @Override
    public Runtime pullRuntime() {
        return concurrentRuntimePool.pullRuntime();
    }

    @Override
    public void returnRuntime(Runtime runtime) {
        concurrentRuntimePool.returnRuntime(runtime);
    }

    @Override
    public Formatter getRealFormatter() {
        return formatter;
    }

    @Override
    public Reporter getRealReporter() {
        return reporter;
    }

    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }
}
