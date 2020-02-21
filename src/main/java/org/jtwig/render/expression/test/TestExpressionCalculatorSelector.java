package org.jtwig.render.expression.test;

import java.util.Optional;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.render.expression.test.calculator.TestExpressionCalculator;

import java.util.Map;

public class TestExpressionCalculatorSelector {
    private final Map<Class<? extends TestExpression>, TestExpressionCalculator> calculatorMap;

    public TestExpressionCalculatorSelector(Map<Class<? extends TestExpression>, TestExpressionCalculator> calculatorMap) {
        this.calculatorMap = calculatorMap;
    }

    public Optional<TestExpressionCalculator> calculatorFor (TestExpression expression) {
        return Optional.ofNullable(calculatorMap.get(expression.getClass()));
    }
}
