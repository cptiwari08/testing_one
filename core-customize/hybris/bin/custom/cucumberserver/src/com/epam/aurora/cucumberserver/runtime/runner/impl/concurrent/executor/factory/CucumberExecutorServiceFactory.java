package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.executor.factory;

import java.util.concurrent.ExecutorService;

public interface CucumberExecutorServiceFactory {
    ExecutorService create();
}
