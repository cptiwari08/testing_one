package com.epam.aurora.cucumberserver.runtime.config.report;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileSystemReportOptionsSource implements CucumberReportOptionsSource {

    private String pluginId;

    private String reportPath;

    public FileSystemReportOptionsSource(String pluginId, String reportPath) {
        this.pluginId = Objects.requireNonNull(pluginId);
        this.reportPath = Objects.requireNonNull(reportPath);
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("--plugin", String.format("%s:%s", pluginId, reportPath));
    }

    public String getReportPath() {
        return reportPath;
    }
}
