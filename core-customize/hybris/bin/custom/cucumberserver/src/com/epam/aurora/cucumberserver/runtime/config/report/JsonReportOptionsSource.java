package com.epam.aurora.cucumberserver.runtime.config.report;

public class JsonReportOptionsSource extends FileSystemReportOptionsSource {

    public JsonReportOptionsSource(String reportPath) {
        super("json", reportPath);
    }

}
