package org.jtwig.property.strategy.method.argument;

import org.jtwig.util.Collections2;

import java.util.Map;

public class AssignableTypes {
    private final IsNativeType isNativeType;
    private static final Map<Class, Class> BOXED_TYPE = Collections2
            .<Class, Class>map(Integer.TYPE, Integer.class)
            .put(Float.TYPE, Float.class)
            .put(Double.TYPE, Double.class)
            .put(Long.TYPE, Long.class)
            .put(Boolean.TYPE, Boolean.class)
            .put(Character.TYPE, Character.class)
            .build();

    public AssignableTypes(IsNativeType isNativeType) {
        this.isNativeType = isNativeType;
    }

    public boolean isAssignable(Class methodArgument, Class providedArgument) {
        Class compareMethodArgument = methodArgument;
        Class compareProvidedArgument = providedArgument;

        if (isNativeType.isNative(methodArgument)) {
            compareMethodArgument = BOXED_TYPE.get(methodArgument);
        }

        if (isNativeType.isNative(providedArgument)) {
            compareProvidedArgument = BOXED_TYPE.get(providedArgument);
        }

        return compareMethodArgument.isAssignableFrom(compareProvidedArgument);
    }
}
