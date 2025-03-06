package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.executor.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.hybris.platform.servicelayer.config.ConfigurationService;

public class CucumberExecutorServiceFactoryImpl implements CucumberExecutorServiceFactory {
    private String numberOfThreadsPropertyName;
    private ConfigurationService configurationService;
    private ThreadFactory threadFactory;

    public CucumberExecutorServiceFactoryImpl(String numberOfThreadsPropertyName, ConfigurationService configurationService, ThreadFactory threadFactory) {
        this.numberOfThreadsPropertyName = numberOfThreadsPropertyName;
        this.configurationService = configurationService;
        this.threadFactory = threadFactory;
    }

    @Override
    public ExecutorService create() {
        int maxThreadsNumber = configurationService.getConfiguration().getInt(numberOfThreadsPropertyName);
        return new ThreadPoolExecutor(maxThreadsNumber, maxThreadsNumber, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
    }
}
