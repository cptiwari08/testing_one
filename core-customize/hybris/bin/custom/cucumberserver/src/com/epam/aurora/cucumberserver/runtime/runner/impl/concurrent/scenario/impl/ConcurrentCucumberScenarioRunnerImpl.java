package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.scenario.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContext;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.scenario.ConcurrentCucumberScenarioRunner;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResultFactory;
import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecorder;
import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecordingContext;
import com.epam.aurora.cucumberserver.util.interaction.recorder.MethodCallRecordingContextFactory;
import cucumber.runtime.Runtime;
import cucumber.runtime.model.CucumberTagStatement;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;

public class ConcurrentCucumberScenarioRunnerImpl implements ConcurrentCucumberScenarioRunner {
    private MethodCallRecordingContextFactory methodCallRecordingContextFactory;
    private TestExecutionResultFactory testExecutionResultFactory;

    public ConcurrentCucumberScenarioRunnerImpl(MethodCallRecordingContextFactory methodCallRecordingContextFactory,
            TestExecutionResultFactory testExecutionResultFactory) {
        this.methodCallRecordingContextFactory = methodCallRecordingContextFactory;
        this.testExecutionResultFactory = testExecutionResultFactory;
    }

    @Override
    public Future<Callable<TestExecutionResult>> runAsync(CucumberTagStatement scenario, ConcurrentCucumberContext concurrentCucumberContext) {
        ExecutorService executorService = concurrentCucumberContext.getExecutorService();

        MethodCallRecordingContext methodCallRecordingContext = methodCallRecordingContextFactory.create(Formatter.class, Reporter.class);
        return executorService.submit(() -> runScenario(scenario, concurrentCucumberContext, methodCallRecordingContext));
    }

    private Callable<TestExecutionResult> runScenario(CucumberTagStatement scenario, ConcurrentCucumberContext concurrentCucumberContext,
            MethodCallRecordingContext methodCallRecordingContext) {
        Runtime runtime = concurrentCucumberContext.pullRuntime();
        
        Formatter proxyFormatter = (Formatter) methodCallRecordingContext.getProxyToRecordMethodCalls();
        Reporter proxyReporter = (Reporter) methodCallRecordingContext.getProxyToRecordMethodCalls();

        scenario.run(proxyFormatter, proxyReporter, runtime);

        TestExecutionResult result = testExecutionResultFactory.create(runtime);
        concurrentCucumberContext.returnRuntime(runtime);

        return () -> recordToReportAndReturnResult(methodCallRecordingContext.getMethodCallRecorder(), concurrentCucumberContext, result);
    }

    private TestExecutionResult recordToReportAndReturnResult(MethodCallRecorder methodCallRecorder, ConcurrentCucumberContext concurrentCucumberContext,
            TestExecutionResult result) {
        Formatter realFormatter = concurrentCucumberContext.getRealFormatter();
        Reporter realReporter = concurrentCucumberContext.getRealReporter();

        methodCallRecorder.applyRecordedMethodCalls(realFormatter, realReporter);
        return result;
    }
}
