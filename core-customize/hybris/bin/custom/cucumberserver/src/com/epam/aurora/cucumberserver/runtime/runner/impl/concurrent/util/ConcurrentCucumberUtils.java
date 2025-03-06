package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.util;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeException;

public class ConcurrentCucumberUtils {
    private ConcurrentCucumberUtils() {
    }

    public static <T> T extractFromFuture(Future<T> futureScenarioResult) {
        try {
            return futureScenarioResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new CucumberRuntimeException(MessageFormat.format("Error during retrieving from future: {0}", futureScenarioResult), e);
        }
    }

    public static <T> T extractFromCallable(Callable<T> futureScenarioResult) {
        try {
            return futureScenarioResult.call();
        } catch (Exception e) {
            throw new CucumberRuntimeException(MessageFormat.format("Error during retrieving from callable: {0}", futureScenarioResult), e);
        }
    }
}
