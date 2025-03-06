package com.epam.aurora.cucumberserver.runtime.config;

import static com.epam.aurora.cucumberserver.runtime.config.resourceloader.NoOpCucumberResourceOptionsSource.NO_OP_INSTANCE;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.epam.aurora.cucumberserver.runtime.HybrisSpringObjectFactory;
import com.epam.aurora.cucumberserver.runtime.config.report.CucumberReportOptionsSource;
import com.epam.aurora.cucumberserver.runtime.config.resourceloader.CucumberResourceLoaderOptionsSource;

import cucumber.runtime.Backend;
import cucumber.runtime.java.JavaBackend;

public class CucumberRuntimeOptions {

    private ClassLoader classLoader = getClass().getClassLoader();

    private List<String> tags = new ArrayList<>();
    private List<String> plugins = new ArrayList<>();
    private List<String> glueCode = new ArrayList<>();

    private Supplier<List<? extends Backend>> executionBackendsFactory = () -> new ArrayList<>(asList(new JavaBackend(new HybrisSpringObjectFactory())));

    private CucumberReportOptionsSource reportOptions = Collections::emptyList;
    private CucumberResourceLoaderOptionsSource resourceLoaderOptions = NO_OP_INSTANCE;

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = Objects.requireNonNull(classLoader);
    }

    public List<? extends Backend> getExecutionBackends() {
        return executionBackendsFactory.get();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = Objects.requireNonNull(tags);
    }

    public List<String> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<String> plugins) {
        this.plugins = Objects.requireNonNull(plugins);
    }

    public List<String> getGlueCode() {
        return glueCode;
    }

    public void setGlueCode(List<String> glueCode) {
        this.glueCode = Objects.requireNonNull(glueCode);
    }

    public CucumberResourceLoaderOptionsSource getResourceLoaderOptions() {
        return resourceLoaderOptions;
    }

    public void setResourceLoaderOptions(CucumberResourceLoaderOptionsSource resourceLoaderOptions) {
        this.resourceLoaderOptions = resourceLoaderOptions;
    }

    public CucumberReportOptionsSource getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(CucumberReportOptionsSource reportOptions) {
        this.reportOptions = reportOptions;
    }

}
