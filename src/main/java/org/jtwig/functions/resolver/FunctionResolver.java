package org.jtwig.functions.resolver;

import java.util.Optional;
import java.util.function.Supplier;

import org.jtwig.functions.FunctionArguments;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public interface FunctionResolver {
    Optional<Supplier<Object>> resolve (RenderRequest request, Position position, String functionName, FunctionArguments arguments);
}
