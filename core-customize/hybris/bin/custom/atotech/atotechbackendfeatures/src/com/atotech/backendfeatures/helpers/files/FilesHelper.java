package com.atotech.backendfeatures.helpers.files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Component
public class FilesHelper {

    public String readTemplateWithPlaceholdersReplacing(String templatePath, Map<String, String> values) {
        return StrSubstitutor.replace(readFile(templatePath), values);
    }

    public String readFile(String path) {
        try {
            return FileUtils.readFileToString(getFileFromPath(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public File getFileFromPath(String path) {
        URL filePath = getClass().getResource(path);
        if (Objects.isNull(filePath)) {
            throw new IllegalArgumentException("Resource for given path doesn't exist: " + path);
        }

        File file = new File(filePath.getFile());
        if (!file.exists()) {
            throw new IllegalArgumentException("File doesn't exist: " + path);
        }
        return file;
    }
}
