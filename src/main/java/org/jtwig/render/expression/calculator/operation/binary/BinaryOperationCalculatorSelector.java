package org.jtwig.render.expression.calculator.operation.binary;

import java.util.Optional;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;

import java.util.Map;

public class BinaryOperationCalculatorSelector {
    private final Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> calculatorMap;

    public BinaryOperationCalculatorSelector(Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> calculatorMap) {
        this.calculatorMap = calculatorMap;
    }

    public Optional<BinaryOperationCalculator> calculatorFor (BinaryOperator operator) {
        return Optional.ofNullable(calculatorMap.get(operator.getClass()));
    }
}
