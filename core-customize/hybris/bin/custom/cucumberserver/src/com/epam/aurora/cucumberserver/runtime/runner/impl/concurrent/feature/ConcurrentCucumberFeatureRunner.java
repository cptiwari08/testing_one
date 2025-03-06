package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.feature;

import java.util.concurrent.Callable;

import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContext;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import cucumber.runtime.model.CucumberFeature;

public interface ConcurrentCucumberFeatureRunner {
    Callable<TestExecutionResult> runAsync(CucumberFeature feature, ConcurrentCucumberContext concurrentCucumberContext);
}
