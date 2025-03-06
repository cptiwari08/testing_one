package com.epam.aurora.cucumberserver.runtime.runner.result;

import cucumber.runtime.Runtime;

public interface TestExecutionResultFactory {
    TestExecutionResult create(Runtime runtime);
}
