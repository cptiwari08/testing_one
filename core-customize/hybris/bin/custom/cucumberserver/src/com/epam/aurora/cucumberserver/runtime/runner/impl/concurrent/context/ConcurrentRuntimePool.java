package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context;

import cucumber.runtime.Runtime;

public interface ConcurrentRuntimePool {
    Runtime pullRuntime();
    
    void returnRuntime(Runtime runtime);
}
