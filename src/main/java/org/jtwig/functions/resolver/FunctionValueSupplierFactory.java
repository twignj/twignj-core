package org.jtwig.functions.resolver;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;

import java.util.function.Supplier;

public class FunctionValueSupplierFactory {
    public Supplier<Object> create (JtwigFunction function, FunctionRequest request) {
        return new FunctionValueSupplier(function, request);
    }
}
