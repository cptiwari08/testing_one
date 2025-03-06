package com.epam.aurora.cucumberserver.runtime.config.resourceloader.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.input.ReaderInputStream;

import cucumber.runtime.io.Resource;

public class InMemoryCucumberResource implements Resource {

    private String name;
    private String text;

    public InMemoryCucumberResource(String name, String text) {
        this.name = Objects.requireNonNull(name);
        this.text = Objects.requireNonNull(text);
    }

    @Override
    public String getPath() {
        return name;
    }

    @Override
    public String getAbsolutePath() {
        return name;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ReaderInputStream(new StringReader(text), StandardCharsets.UTF_8);
    }

    @Override
    public String getClassName(String extension) {
        return name;
    }

}