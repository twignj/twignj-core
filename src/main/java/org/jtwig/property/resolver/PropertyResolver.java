package org.jtwig.property.resolver;

import java.util.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;

public interface PropertyResolver {
    Optional<Value> resolve (PropertyResolveRequest request);
}
