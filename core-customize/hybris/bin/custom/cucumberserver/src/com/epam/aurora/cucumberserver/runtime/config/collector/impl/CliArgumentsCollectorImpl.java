package com.epam.aurora.cucumberserver.runtime.config.collector.impl;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.epam.aurora.cucumberserver.runtime.config.CucumberRuntimeOptions;
import com.epam.aurora.cucumberserver.runtime.config.collector.CliArgumentsCollector;

public class CliArgumentsCollectorImpl implements CliArgumentsCollector {
    private final static String TAGS_ARGUMENTS_PREFIX = "--tags";
    private final static String GLUE_ARGUMENTS_PREFIX = "--glue";
    private final static String PLUGIN_ARGUMENTS_PREFIX = "--plugin";
    
    @Override
    public List<String> collect(CucumberRuntimeOptions options) {
        List<String> arguments = new ArrayList<>();

        arguments.addAll(prepareArguments(TAGS_ARGUMENTS_PREFIX, options.getTags()));
        arguments.addAll(prepareArguments(GLUE_ARGUMENTS_PREFIX, options.getGlueCode()));
        arguments.addAll(prepareArguments(PLUGIN_ARGUMENTS_PREFIX, options.getPlugins()));

        arguments.addAll(options.getReportOptions().getOptions());
        arguments.addAll(options.getResourceLoaderOptions().getOptions());

        return arguments;
    }

    private List<String> prepareArguments(String prefix, Collection<String> arguments) {
        return arguments.stream().flatMap(arg -> asList(prefix, arg).stream()).collect(toList());
    }
}
