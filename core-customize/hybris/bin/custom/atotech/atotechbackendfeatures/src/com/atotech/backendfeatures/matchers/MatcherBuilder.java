package com.atotech.backendfeatures.matchers;

import static org.apache.commons.lang3.ObjectUtils.notEqual;

public class MatcherBuilder {

    private final MatcherContext context;
    private boolean isEqual = true;

    public MatcherBuilder(MatcherContext context) {
        this.context = context;
    }

    public MatcherBuilder appendEquals(String testedObjective, Object actual, Object expected) {
        if (isEqual && notEqual(actual, expected)) {
            context.reason = testedObjective;
            context.actual = actual;
            context.expected = expected;
            isEqual = false;
        }
        return this;
    }

    public boolean isEqual() {
        return isEqual;
    }
}
