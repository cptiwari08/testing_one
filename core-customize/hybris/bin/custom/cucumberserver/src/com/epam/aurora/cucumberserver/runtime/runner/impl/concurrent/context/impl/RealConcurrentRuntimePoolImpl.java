package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.IntStream;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.factory.CucumberRuntimeFactory;
import com.epam.aurora.cucumberserver.runtime.runner.CucumberRuntimeException;
import com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context.ConcurrentRuntimePool;
import cucumber.runtime.Runtime;

public class RealConcurrentRuntimePoolImpl implements ConcurrentRuntimePool {
    private BlockingQueue<Runtime> blockingQueue;

    public RealConcurrentRuntimePoolImpl(CucumberRuntimeOptions cucumberRuntimeOptions, CucumberRuntimeFactory cucumberRuntimeFactory, int pullCapacity) {
        initBlockingQueue(cucumberRuntimeOptions, cucumberRuntimeFactory, pullCapacity);
    }

    private void initBlockingQueue(CucumberRuntimeOptions cucumberRuntimeOptions, CucumberRuntimeFactory cucumberRuntimeFactory, int pullCapacity) {
        blockingQueue = new LinkedBlockingDeque<>(pullCapacity);
        IntStream.range(0, pullCapacity)
                .mapToObj(index -> cucumberRuntimeFactory.create(cucumberRuntimeOptions))
                .forEach(blockingQueue::add);
    }

    @Override
    public Runtime pullRuntime() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new CucumberRuntimeException(e);
        }
    }

    @Override
    public void returnRuntime(Runtime runtime) {
        cleanRuntime(runtime);
        blockingQueue.add(runtime);
    }

    private void cleanRuntime(Runtime runtime) {
        runtime.getErrors().clear();
    }
}
