package com.epam.aurora.cucumberserver.runtime.config.report;

public class HtmlReportOptionsSource extends FileSystemReportOptionsSource {

    public HtmlReportOptionsSource(String reportPath) {
        super("html", reportPath);
    }

}
