package com.epam.aurora.cucumberserver.runtime.runner;

import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;

public interface CucumberRuntimeRunner {
    /**
     * Can throw {@link com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeException}
     *
     * @param options - configurations to run tests
     * @return
     */
    TestExecutionResult runCucumberEngineWithOptions(CucumberRuntimeOptions options);
}
