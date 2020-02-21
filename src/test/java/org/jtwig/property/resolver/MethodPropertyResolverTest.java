package org.jtwig.property.resolver;

import java.util.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.property.strategy.method.ArgumentsConverter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class MethodPropertyResolverTest {
    private final JavaMethod javaMethod = mock(JavaMethod.class);
    private final ArgumentsConverter argumentsConverter = mock(ArgumentsConverter.class);
    private MethodPropertyResolver underTest = new MethodPropertyResolver(javaMethod, argumentsConverter);

    @Test
    public void argumentsConverterFails() throws Exception {
        PropertyResolveRequest propertyResolveRequest = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);

        given(propertyResolveRequest.getArguments()).willReturn(asList());
        given(argumentsConverter.convert(eq(javaMethod), argThat(arrayWithSize(0)))).willReturn(Optional.empty());

        Optional<Value> result = underTest.resolve(propertyResolveRequest);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void invocationFails() throws Exception {
        Object[] arguments = {};
        Object context = new Object();
        PropertyResolveRequest propertyResolveRequest = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);

        given(propertyResolveRequest.getArguments()).willReturn(asList());
        given(propertyResolveRequest.getContext()).willReturn(context);
        given(argumentsConverter.convert(eq(javaMethod), argThat(arrayWithSize(0)))).willReturn(Optional.of(arguments));
        given(javaMethod.invoke(context, arguments)).willThrow(InvocationTargetException.class);

        Optional<Value> result = underTest.resolve(propertyResolveRequest);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void invocationFailsWithIllegalAccess() throws Exception {
        Object[] arguments = {};
        Object context = new Object();
        PropertyResolveRequest propertyResolveRequest = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);

        given(propertyResolveRequest.getArguments()).willReturn(asList());
        given(propertyResolveRequest.getContext()).willReturn(context);
        given(argumentsConverter.convert(eq(javaMethod), argThat(arrayWithSize(0)))).willReturn(Optional.of(arguments));
        given(javaMethod.invoke(context, arguments)).willThrow(IllegalAccessException.class);

        Optional<Value> result = underTest.resolve(propertyResolveRequest);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void invocationFailsWithIllegalArgumentException() throws Exception {
        Object[] arguments = {};
        Object context = new Object();
        PropertyResolveRequest propertyResolveRequest = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);

        given(propertyResolveRequest.getArguments()).willReturn(asList());
        given(propertyResolveRequest.getContext()).willReturn(context);
        given(argumentsConverter.convert(eq(javaMethod), argThat(arrayWithSize(0)))).willReturn(Optional.of(arguments));
        given(javaMethod.invoke(context, arguments)).willThrow(IllegalArgumentException.class);

        Optional<Value> result = underTest.resolve(propertyResolveRequest);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void methodPropertyContextNull() throws Exception {
        PropertyResolveRequest propertyResolveRequest = mock(PropertyResolveRequest.class, RETURNS_DEEP_STUBS);

        given(propertyResolveRequest.getArguments()).willReturn(asList());
        given(propertyResolveRequest.getContext()).willReturn(null);

        Optional<Value> result = underTest.resolve(propertyResolveRequest);

        assertEquals(Optional.empty(), result);
    }
}