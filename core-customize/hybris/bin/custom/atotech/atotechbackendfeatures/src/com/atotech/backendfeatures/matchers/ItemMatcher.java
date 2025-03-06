package com.atotech.backendfeatures.matchers;

import com.atotech.backendfeatures.spel.FieldValueExtractor;
import de.hybris.platform.core.model.ItemModel;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.Map;

import static java.lang.String.format;

public class ItemMatcher extends BaseMatcher<ItemModel> {

    private static final String MISMATCH_REASON = "%s missmatched. Actual = %s. Expected = %s";

    private final Map<String, String> expectedValues;
    private final MatcherContext matcherContext;

    public ItemMatcher(Map<String, String> expectedValues) {
        this.expectedValues = expectedValues;
        this.matcherContext = new MatcherContext();
    }

    @Override
    public boolean matches(Object item) {
        if (!(item instanceof ItemModel)) {
            return false;
        }

        FieldValueExtractor fieldValueExtractor = new FieldValueExtractor(item);

        MatcherBuilder matcherBuilder = new MatcherBuilder(matcherContext);
        expectedValues.forEach((field, expectedValue) ->{
            matcherBuilder.appendEquals(field, fieldValueExtractor.getValue(field), expectedValue);
        });

        return matcherBuilder.isEqual();
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("Item should have expected values");
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        String mismatchText = format(MISMATCH_REASON, matcherContext.reason, matcherContext.actual, matcherContext.expected);
        description.appendText(mismatchText);
    }

    public static ItemMatcher hasExpectedValues(Map<String, String> expectedValues) {
        return new ItemMatcher(expectedValues);
    }
}
