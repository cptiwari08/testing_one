package com.atotech.backendfeatures.spel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class FieldValueExtractor {

    private static final Logger LOG = LoggerFactory.getLogger(FieldValueExtractor.class);

    private final ExpressionParser expressionParser;
    private final EvaluationContext evaluationContext;

    public FieldValueExtractor(Object source) {
        this.evaluationContext = new StandardEvaluationContext(source);
        this.expressionParser = new SpelExpressionParser();
    }

    public Object getValue(String path) {
        Expression expression = expressionParser.parseExpression(path);
        try {
            return expression.getValue(evaluationContext);
        } catch (SpelEvaluationException e) {
            Object source = evaluationContext.getRootObject().getValue();
            LOG.warn("Cannot extract value {} from object {}. Return null value", path, source, e);
            return null;
        }
    }
}
