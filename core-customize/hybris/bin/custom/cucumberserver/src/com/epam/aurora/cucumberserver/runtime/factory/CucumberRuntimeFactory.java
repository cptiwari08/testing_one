package com.epam.aurora.cucumberserver.runtime.factory;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import cucumber.runtime.Runtime;

public interface CucumberRuntimeFactory {
    Runtime create(CucumberRuntimeOptions options);
}
