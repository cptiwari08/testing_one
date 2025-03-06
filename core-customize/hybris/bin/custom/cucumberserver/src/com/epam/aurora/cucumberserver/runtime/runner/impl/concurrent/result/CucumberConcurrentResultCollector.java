package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.result;

import java.util.List;

import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;

public interface CucumberConcurrentResultCollector {
    TestExecutionResult collectResult(List<TestExecutionResult> futureScenarioResults);
}
