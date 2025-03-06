package com.epam.aurora.cucumberserver.runtime.runner.result;

import java.util.ArrayList;
import java.util.List;

public class TestExecutionResult {

    private List<String> errors = new ArrayList<>();
    private List<String> snippets = new ArrayList<>();

    public TestExecutionResult() {
    }

    public TestExecutionResult(List<String> errors, List<String> snippets) {
        this.errors = errors;
        this.snippets = snippets;
    }

    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    public List<String> getSnippets() {
        return snippets;
    }
    public void setSnippets(List<String> snippets) {
        this.snippets = snippets;
    }

}
