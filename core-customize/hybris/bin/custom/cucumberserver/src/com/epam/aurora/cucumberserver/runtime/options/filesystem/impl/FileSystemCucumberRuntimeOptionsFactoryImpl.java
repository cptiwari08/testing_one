package com.epam.aurora.cucumberserver.runtime.options.filesystem.impl;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.defaultIfEmpty;

import java.util.List;

import org.springframework.util.MultiValueMap;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.config.glue.GlueCodeLocationsFactory;
import com.epam.aurora.cucumberserver.runtime.config.report.CucumberReportOptionsSource;
import com.epam.aurora.cucumberserver.runtime.config.report.HtmlReportOptionsSource;
import com.epam.aurora.cucumberserver.runtime.config.report.JsonReportOptionsSource;
import com.epam.aurora.cucumberserver.runtime.config.resourceloader.CucumberResourceLoaderOptionsSource;
import com.epam.aurora.cucumberserver.runtime.config.resourceloader.MultiCucumberResourceLoaderOptionsSource;
import com.epam.aurora.cucumberserver.runtime.options.filesystem.FileSystemCucumberRuntimeOptionsFactory;
import de.hybris.platform.servicelayer.config.ConfigurationService;

public class FileSystemCucumberRuntimeOptionsFactoryImpl implements FileSystemCucumberRuntimeOptionsFactory {
    private ConfigurationService configurationService;
    private GlueCodeLocationsFactory glueCodeLocationsFactory;

    public FileSystemCucumberRuntimeOptionsFactoryImpl(ConfigurationService configurationService, GlueCodeLocationsFactory glueCodeLocationsFactory) {
        this.configurationService = configurationService;
        this.glueCodeLocationsFactory = glueCodeLocationsFactory;
    }

    @Override
    public CucumberRuntimeOptions create(MultiValueMap<String, String> requestParams) {
        CucumberRuntimeOptions options = new CucumberRuntimeOptions();

        options.setGlueCode(glueCodeLocationsFactory.get());
        options.setPlugins(getPlugins());
        options.setTags(getExecutionTags(requestParams));
        options.setReportOptions(getReportOptions(requestParams));
        options.setResourceLoaderOptions(createFileSystemResourceLoaderOptions(requestParams, options.getClassLoader()));

        return options;
    }

    private String getReportFormat(MultiValueMap<String, String> requestParams) {
        return defaultIfEmpty(requestParams.getFirst(REPORT_FORMAT_KEY), DEFAULT_REPORT_FORMAT);
    }

    private String getFeaturePath(MultiValueMap<String, String> requestParams) {
        return defaultIfEmpty(requestParams.getFirst(FEATURES_PATH_KEY), configurationService.getConfiguration().getString(FEATURES_PATH_PROPERTY));
    }

    private String getReportPath(MultiValueMap<String, String> requestParams) {
        return defaultIfEmpty(requestParams.getFirst(REPORT_PATH_KEY), configurationService.getConfiguration().getString(REPORT_PATH_PROPERTY));
    }

    private CucumberResourceLoaderOptionsSource createFileSystemResourceLoaderOptions(MultiValueMap<String, String> requestParams, ClassLoader classLoader) {
        return new MultiCucumberResourceLoaderOptionsSource(getFeaturePath(requestParams), classLoader);
    }

    private CucumberReportOptionsSource getReportOptions(MultiValueMap<String, String> requestParams) {
        String reportFormat = getReportFormat(requestParams);

        if (HTML_REPORT_TYPE.equals(reportFormat)) {
            return new HtmlReportOptionsSource(getReportPath(requestParams));
        }

        return new JsonReportOptionsSource(getReportPath(requestParams) + REPORT_JSON_FILE);
    }

    private List<String> getExecutionTags(MultiValueMap<String, String> requestParams) {
        List<String> activeRequestedTags = requestParams.getOrDefault(CUCUMBER_TAGS_KEY, emptyList());

        if (activeRequestedTags.isEmpty()) {
            activeRequestedTags = asList(configurationService.getConfiguration().getString(CUCUMBER_TAGS_PROPERTY));
        }

        return splitTags(activeRequestedTags);
    }

    private List<String> splitTags(List<String> tags) {
        return tags.stream().flatMap(tag -> stream(tag.split(TAGS_SPLITTER))).collect(toList());
    }

    private List<String> getPlugins() {
        return asList(PROGRESS_PLUGIN_NAME);
    }
}
