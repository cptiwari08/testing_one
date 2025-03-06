package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context;

import java.util.concurrent.ExecutorService;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;

public interface ConcurrentCucumberContext {
    CucumberRuntimeOptions getCucumberRuntimeOptions();

    RuntimeOptions getRuntimeOptions();

    /**
     * Just get some Runtime
     *
     * @return
     */
    Runtime getRuntime();

    /**
     * Pull Runtime from chain
     *
     * @return
     */
    Runtime pullRuntime();

    /**
     * Return Runtime back to chain
     */
    void returnRuntime(Runtime runtime);

    Formatter getRealFormatter();

    Reporter getRealReporter();

    ExecutorService getExecutorService();
}
