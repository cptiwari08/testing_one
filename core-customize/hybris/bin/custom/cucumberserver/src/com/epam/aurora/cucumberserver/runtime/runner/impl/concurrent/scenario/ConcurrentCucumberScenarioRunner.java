package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.scenario;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContext;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import cucumber.runtime.model.CucumberTagStatement;

public interface ConcurrentCucumberScenarioRunner {
    Future<Callable<TestExecutionResult>> runAsync(CucumberTagStatement scenario, ConcurrentCucumberContext concurrentCucumberContext);
}
