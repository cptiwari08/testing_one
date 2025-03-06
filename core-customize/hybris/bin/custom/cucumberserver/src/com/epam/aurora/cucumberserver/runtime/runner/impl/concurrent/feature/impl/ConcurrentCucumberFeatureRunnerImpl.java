package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.feature.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContext;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.feature.ConcurrentCucumberFeatureRunner;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.result.CucumberConcurrentResultCollector;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.scenario.ConcurrentCucumberScenarioRunner;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.util.ConcurrentCucumberUtils;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import cucumber.runtime.model.CucumberFeature;
import gherkin.formatter.Formatter;

public class ConcurrentCucumberFeatureRunnerImpl implements ConcurrentCucumberFeatureRunner {
    private ConcurrentCucumberScenarioRunner concurrentCucumberScenarioRunner;
    private CucumberConcurrentResultCollector cucumberConcurrentResultCollector;

    public ConcurrentCucumberFeatureRunnerImpl(ConcurrentCucumberScenarioRunner concurrentCucumberScenarioRunner,
            CucumberConcurrentResultCollector cucumberConcurrentResultCollector) {
        this.concurrentCucumberScenarioRunner = concurrentCucumberScenarioRunner;
        this.cucumberConcurrentResultCollector = cucumberConcurrentResultCollector;
    }

    @Override
    public Callable<TestExecutionResult> runAsync(CucumberFeature feature, ConcurrentCucumberContext concurrentCucumberContext) {
        List<Future<Callable<TestExecutionResult>>> scenariosFutureResults = feature.getFeatureElements().stream()
                .map(scenario -> concurrentCucumberScenarioRunner.runAsync(scenario, concurrentCucumberContext))
                .collect(Collectors.toList());

        return () -> waitForScenariosWriteToReportAndReturnResult(feature, concurrentCucumberContext, scenariosFutureResults);
    }

    private TestExecutionResult waitForScenariosWriteToReportAndReturnResult(CucumberFeature feature, ConcurrentCucumberContext concurrentCucumberContext,
            List<Future<Callable<TestExecutionResult>>> scenariosFutureResults) {
        Formatter formatter = concurrentCucumberContext.getRealFormatter();

        formatter.uri(feature.getPath());
        formatter.feature(feature.getGherkinFeature());

        TestExecutionResult testExecutionResult = cucumberConcurrentResultCollector.collectResult(
                extractScenariosResultsAndWriteToReport(
                        waitForScenariosCallableResults(scenariosFutureResults)));

        formatter.eof();

        return testExecutionResult;
    }

    private List<TestExecutionResult> extractScenariosResultsAndWriteToReport(List<Callable<TestExecutionResult>> callables) {
        return callables.stream()
                .map(ConcurrentCucumberUtils::extractFromCallable)
                .collect(Collectors.toList());
    }

    private List<Callable<TestExecutionResult>> waitForScenariosCallableResults(List<Future<Callable<TestExecutionResult>>> scenariosFutureResults) {
        return scenariosFutureResults.stream()
                .map(ConcurrentCucumberUtils::extractFromFuture)
                .collect(Collectors.toList());
    }
}
