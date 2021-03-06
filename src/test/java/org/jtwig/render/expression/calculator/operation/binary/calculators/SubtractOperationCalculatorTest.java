package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SubtractOperationCalculatorTest {
    private SubtractOperationCalculator underTest = new SubtractOperationCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        BigDecimal left = mock(BigDecimal.class);
        BigDecimal right = mock(BigDecimal.class);
        BigDecimal expected = mock(BigDecimal.class);

        when(left.subtract(right)).thenReturn(expected);

        BigDecimal result = underTest.calculate(request, left, right);

        assertSame(expected, result);
    }
}