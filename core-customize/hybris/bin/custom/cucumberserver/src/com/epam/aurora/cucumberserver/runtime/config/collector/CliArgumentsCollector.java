package com.epam.aurora.cucumberserver.runtime.config.collector;

import java.util.List;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;

public interface CliArgumentsCollector {
    List<String> collect(CucumberRuntimeOptions options);
}
