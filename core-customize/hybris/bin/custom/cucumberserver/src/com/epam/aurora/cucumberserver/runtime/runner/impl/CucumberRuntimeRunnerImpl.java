package com.epam.aurora.cucumberserver.runtime.runner.impl;

import java.io.IOException;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.factory.CucumberRuntimeFactory;
import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeException;
import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeRunner;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResultFactory;
import cucumber.runtime.Runtime;

public class CucumberRuntimeRunnerImpl implements CucumberRuntimeRunner {
    private CucumberRuntimeFactory cucumberRuntimeFactory;
    private TestExecutionResultFactory testExecutionResultFactory;

    public CucumberRuntimeRunnerImpl(CucumberRuntimeFactory cucumberRuntimeFactory, TestExecutionResultFactory testExecutionResultFactory) {
        this.cucumberRuntimeFactory = cucumberRuntimeFactory;
        this.testExecutionResultFactory = testExecutionResultFactory;
    }

    @Override
    public TestExecutionResult runCucumberEngineWithOptions(CucumberRuntimeOptions options) {
        Runtime runtime = cucumberRuntimeFactory.create(options);

        try {
            runtime.run();
            return testExecutionResultFactory.create(runtime);
        } catch (IOException e) {
            throw new CucumberRuntimeException(e);
        }
    }
}
