package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.result.impl;

import java.util.List;

import org.apache.commons.collections4.ListUtils;

import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.result.CucumberConcurrentResultCollector;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;

public class CucumberConcurrentResultCollectorImpl implements CucumberConcurrentResultCollector {
    public TestExecutionResult collectResult(List<TestExecutionResult> futureScenarioResults) {
        return futureScenarioResults.stream().reduce(new TestExecutionResult(), this::mergeTestExecutionResults);
    }

    private TestExecutionResult mergeTestExecutionResults(TestExecutionResult left, TestExecutionResult right) {
        List<String> errors = ListUtils.union(left.getErrors(), right.getErrors());
        List<String> snippets = ListUtils.union(left.getSnippets(), right.getSnippets());

        return new TestExecutionResult(errors, snippets);
    }

}
