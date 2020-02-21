package org.jtwig.render.expression.calculator.enumerated;

import java.util.Optional;
import org.jtwig.render.RenderRequest;

import java.util.List;

public interface EnumerationListStrategy {
    Optional<List<Object>> enumerate(RenderRequest request, Object left, Object right);
}
