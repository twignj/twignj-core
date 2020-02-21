package org.jtwig.functions.resolver;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;

import java.util.function.Supplier;

public class FunctionValueSupplier implements Supplier<Object> {
    private final JtwigFunction jtwigFunction;
    private final FunctionRequest request;

    public FunctionValueSupplier(JtwigFunction jtwigFunction, FunctionRequest request) {
        this.jtwigFunction = jtwigFunction;
        this.request = request;
    }

    @Override
    public Object get() {
        return jtwigFunction.execute(request);
    }
}
