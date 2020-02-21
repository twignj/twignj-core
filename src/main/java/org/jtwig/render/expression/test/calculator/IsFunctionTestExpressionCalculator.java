package org.jtwig.render.expression.test.calculator;

import org.jtwig.functions.FunctionArguments;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.test.IsFunctionTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

import java.util.Optional;
import java.util.function.Supplier;

public class IsFunctionTestExpressionCalculator implements TestExpressionCalculator<IsFunctionTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, IsFunctionTestExpression test, Expression argument) {
        if (argument instanceof VariableExpression) {
            Optional<Supplier<Object>> function = request.getEnvironment().getFunctionResolver().resolve(request, position, ((VariableExpression) argument).getIdentifier(), FunctionArguments.empty());
            return function.isPresent();
        } else {
            return false;
        }
    }
}
