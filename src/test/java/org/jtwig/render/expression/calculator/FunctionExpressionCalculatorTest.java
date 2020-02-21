package org.jtwig.render.expression.calculator;

import java.util.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionArguments;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class FunctionExpressionCalculatorTest {
    private final FunctionArgumentsFactory argumentsFactory = mock(FunctionArgumentsFactory.class);
    private FunctionExpressionCalculator underTest = new FunctionExpressionCalculator(argumentsFactory);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateNotFound() throws Exception {
        String function = "function";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        FunctionExpression expression = mock(FunctionExpression.class);
        Expression argument1 = mock(Expression.class);
        Expression argument2 = mock(Expression.class);
        Position position = mock(Position.class);
        FunctionArguments functionArguments = mock(FunctionArguments.class);
        Object argument1Value = new Object();
        Object argument2Value = new Object();
        List<Expression> expressions = asList(argument1, argument2);

        when(argumentsFactory.create(eq(request), eq(expressions))).thenReturn(functionArguments);
        when(expression.getPosition()).thenReturn(position);
        when(expression.getFunctionIdentifier()).thenReturn(function);
        when(expression.getArguments()).thenReturn(expressions);
        when(functionArguments.getValues()).thenReturn(asList(argument1Value, argument2Value));
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument1)).thenReturn(argument1Value);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument2)).thenReturn(argument2Value);
        when(request.getEnvironment().getFunctionResolver().resolve(eq(request), eq(position), eq(function), eq(functionArguments)))
                .thenReturn(Optional.empty());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString(String.format("Unable to resolve function 'function' with arguments [%s, %s]", argument1Value, argument2Value)));

        underTest.calculate(request, expression);
    }

    @Test
    public void calculateFound() throws Exception {
        String function = "function";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        FunctionExpression expression = mock(FunctionExpression.class);
        Expression argument1 = mock(Expression.class);
        Expression argument2 = mock(Expression.class);
        Supplier<Object> supplier = mock(Supplier.class);
        FunctionArguments functionArguments = mock(FunctionArguments.class);
        Position position = mock(Position.class);
        Object argument1Value = new Object();
        Object argument2Value = new Object();
        Object expected = new Object();
        List<Expression> expressions = asList(argument1, argument2);

        when(argumentsFactory.create(eq(request), eq(expressions))).thenReturn(functionArguments);
        when(expression.getPosition()).thenReturn(position);
        when(expression.getFunctionIdentifier()).thenReturn(function);
        when(expression.getArguments()).thenReturn(expressions);
        when(functionArguments.getValues()).thenReturn(asList(argument1Value, argument2Value));
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument1)).thenReturn(argument1Value);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument2)).thenReturn(argument2Value);
        when(request.getEnvironment().getFunctionResolver().resolve(eq(request), eq(position), eq(function), eq(functionArguments)))
                .thenReturn(Optional.of(supplier));
        when(supplier.get()).thenReturn(expected);

        Object result = underTest.calculate(request, expression);

        assertSame(expected, result);
    }
}