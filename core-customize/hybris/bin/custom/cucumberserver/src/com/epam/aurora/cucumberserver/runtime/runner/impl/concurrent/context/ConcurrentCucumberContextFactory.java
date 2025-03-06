package com.epam.aurora.cucumberserver.runtime.runner.impl.concurrent.context;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;

public interface ConcurrentCucumberContextFactory {
    ConcurrentCucumberContext create(CucumberRuntimeOptions cucumberRuntimeOptions);
}
