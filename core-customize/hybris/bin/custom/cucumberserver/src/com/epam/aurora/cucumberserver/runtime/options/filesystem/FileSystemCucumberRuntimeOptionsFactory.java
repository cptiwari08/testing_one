package com.epam.aurora.cucumberserver.runtime.options.filesystem;

import org.springframework.util.MultiValueMap;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;

public interface FileSystemCucumberRuntimeOptionsFactory {
    String REPORT_FORMAT_KEY = "reportFormat";
    String DEFAULT_REPORT_FORMAT = "html";
    String CUCUMBER_TAGS_KEY = "tags";
    String CUCUMBER_TAGS_PROPERTY = "cucumber.tags";
    String FEATURES_PATH_KEY = "featuresPath";
    String FEATURES_PATH_PROPERTY = "features.path";
    String REPORT_PATH_KEY = "reportPath";
    String REPORT_PATH_PROPERTY = "cucumber.report.path";
    String REPORT_JSON_FILE = "cucumber_result.json";
    String HTML_REPORT_TYPE = "html";
    String TAGS_SPLITTER = "--tags";
    String PROGRESS_PLUGIN_NAME = "progress";

    CucumberRuntimeOptions create(MultiValueMap<String, String> requestParams);
}
