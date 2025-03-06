package com.epam.aurora.cucumberserver.runtime.facade.impl;

import org.springframework.util.MultiValueMap;

import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeRunnerFactory;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import com.epam.aurora.cucumberserver.runtime.options.adhoc.AdhocCucumberRuntimeOptionsFactory;
import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.config.report.FileSystemReportOptionsSource;
import com.epam.aurora.cucumberserver.runtime.facade.CucumberRuntimeFacade;
import com.epam.aurora.cucumberserver.runtime.options.filesystem.FileSystemCucumberRuntimeOptionsFactory;

public class CucumberRuntimeFacadeImpl implements CucumberRuntimeFacade {
    private static final String REPORT_STRING_PATTERN = "failures: %s%nundefined: %s%nreport path: %s";
    
    private CucumberRuntimeRunnerFactory cucumberRuntimeRunnerFactory;
    private AdhocCucumberRuntimeOptionsFactory adhocCucumberRuntimeOptionsFactory;
    private FileSystemCucumberRuntimeOptionsFactory fileSystemCucumberRuntimeOptionsFactory;

    public CucumberRuntimeFacadeImpl(CucumberRuntimeRunnerFactory cucumberRuntimeRunnerFactory, AdhocCucumberRuntimeOptionsFactory adhocCucumberRuntimeOptionsFactory,
            FileSystemCucumberRuntimeOptionsFactory fileSystemCucumberRuntimeOptionsFactory) {
        this.cucumberRuntimeRunnerFactory = cucumberRuntimeRunnerFactory;
        this.adhocCucumberRuntimeOptionsFactory = adhocCucumberRuntimeOptionsFactory;
        this.fileSystemCucumberRuntimeOptionsFactory = fileSystemCucumberRuntimeOptionsFactory;
    }

    @Override
    public String runFeaturesFromFileSystem(MultiValueMap<String, String> requestParams) {
        CucumberRuntimeOptions runtimeOptions = fileSystemCucumberRuntimeOptionsFactory.create(requestParams);
        TestExecutionResult executionResult = cucumberRuntimeRunnerFactory.get().runCucumberEngineWithOptions(runtimeOptions);
        return generateExecutionReport(executionResult, ((FileSystemReportOptionsSource) runtimeOptions.getReportOptions()).getReportPath());
    }

    private String generateExecutionReport(TestExecutionResult result, String reportPath) {
        return String.format(REPORT_STRING_PATTERN, result.getErrors().size(), result.getSnippets().size(), reportPath);
    }

    @Override
    public TestExecutionResult runAdhocFeature(String featureText) {
        return cucumberRuntimeRunnerFactory.get().runCucumberEngineWithOptions(adhocCucumberRuntimeOptionsFactory.create(featureText));
    }
}
