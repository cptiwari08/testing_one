package com.epam.aurora.cucumberserver.runtime.options.adhoc;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;

public interface AdhocCucumberRuntimeOptionsFactory {
    CucumberRuntimeOptions create(String featureText);
}
