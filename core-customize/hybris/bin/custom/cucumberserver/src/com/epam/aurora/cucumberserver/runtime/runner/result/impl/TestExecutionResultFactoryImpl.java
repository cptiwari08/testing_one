package com.epam.aurora.cucumberserver.runtime.runner.result.impl;

import static java.util.stream.Collectors.toList;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResult;
import com.epam.aurora.cucumberserver.runtime.runner.result.TestExecutionResultFactory;
import cucumber.runtime.Runtime;

public class TestExecutionResultFactoryImpl implements TestExecutionResultFactory {
    @Override
    public TestExecutionResult create(Runtime runtime) {
        TestExecutionResult result = new TestExecutionResult();

        result.setErrors(runtime.getErrors().stream().map(ExceptionUtils::getStackTrace).collect(toList()));
        result.setSnippets(runtime.getSnippets());

        return result;
    }
}
