package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeRunner;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContext;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentCucumberContextFactory;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.feature.ConcurrentCucumberFeatureRunner;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.result.CucumberConcurrentResultCollector;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.util.ConcurrentCucumberUtils;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import cucumber.api.StepDefinitionReporter;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.model.CucumberFeature;
import gherkin.formatter.Formatter;

public class ConcurrentCucumberRuntimeRunner implements CucumberRuntimeRunner {
    private static final Logger LOG = Logger.getLogger(ConcurrentCucumberRuntimeRunner.class);
    private static final String START_CONFIGURATION_LOG_PATTERN = "Cucumber tests starts with {0} features: \n";

    private ConcurrentCucumberContextFactory concurrentCucumberContextFactory;
    private ConcurrentCucumberFeatureRunner concurrentCucumberFeatureRunner;
    private CucumberConcurrentResultCollector cucumberConcurrentResultCollector;

    public ConcurrentCucumberRuntimeRunner(ConcurrentCucumberContextFactory concurrentCucumberContextFactory,
            ConcurrentCucumberFeatureRunner concurrentCucumberFeatureRunner, CucumberConcurrentResultCollector cucumberConcurrentResultCollector) {
        this.concurrentCucumberContextFactory = concurrentCucumberContextFactory;
        this.concurrentCucumberFeatureRunner = concurrentCucumberFeatureRunner;
        this.cucumberConcurrentResultCollector = cucumberConcurrentResultCollector;
    }

    @Override
    public TestExecutionResult runCucumberEngineWithOptions(CucumberRuntimeOptions options) {
        ConcurrentCucumberContext concurrentCucumberContext = concurrentCucumberContextFactory.create(options);
        List<CucumberFeature> features = loadCucumberFeatures(concurrentCucumberContext.getCucumberRuntimeOptions(),
                concurrentCucumberContext.getRuntimeOptions());
        logStartConfiguration(features);
        reportStepDefinition(concurrentCucumberContext);

        TestExecutionResult result = runFeatures(features, concurrentCucumberContext);

        closeFormatter(concurrentCucumberContext.getRealFormatter());
        return result;
    }

    private void logStartConfiguration(List<CucumberFeature> features) {
        LOG.info(MessageFormat.format(START_CONFIGURATION_LOG_PATTERN, features.size()));
    }

    private TestExecutionResult runFeatures(List<CucumberFeature> features, ConcurrentCucumberContext concurrentCucumberContext) {
        return cucumberConcurrentResultCollector.collectResult(
                waitFeaturesGetResultsAndWriteToReport(
                        runFeaturesAsync(features, concurrentCucumberContext)));
    }

    private List<TestExecutionResult> waitFeaturesGetResultsAndWriteToReport(List<Callable<TestExecutionResult>> features) {
        return features.stream()
                .map(ConcurrentCucumberUtils::extractFromCallable)
                .collect(Collectors.toList());
    }

    private List<Callable<TestExecutionResult>> runFeaturesAsync(List<CucumberFeature> features, ConcurrentCucumberContext concurrentCucumberContext) {
        return features.stream()
                .map(feature -> concurrentCucumberFeatureRunner.runAsync(feature, concurrentCucumberContext))
                .collect(Collectors.toList());
    }

    private List<CucumberFeature> loadCucumberFeatures(CucumberRuntimeOptions options, RuntimeOptions runtimeOptions) {
        return runtimeOptions.cucumberFeatures(options.getResourceLoaderOptions().getResourceLoader());
    }

    private void closeFormatter(Formatter formatter) {
        formatter.done();
        formatter.close();
    }

    private void reportStepDefinition(ConcurrentCucumberContext concurrentCucumberContext) {
        StepDefinitionReporter stepDefinitionReporter = concurrentCucumberContext.getRuntimeOptions()
                .stepDefinitionReporter(concurrentCucumberContext.getCucumberRuntimeOptions().getClassLoader());
        concurrentCucumberContext.getRuntime().getGlue().reportStepDefinitions(stepDefinitionReporter);
    }
}
