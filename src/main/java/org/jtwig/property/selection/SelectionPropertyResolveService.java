package org.jtwig.property.selection;

import java.util.Optional;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.resolver.request.PropertyResolveRequestFactory;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

public class SelectionPropertyResolveService {
    private final PropertyResolveRequestFactory propertyResolveRequestFactory;

    public SelectionPropertyResolveService(PropertyResolveRequestFactory propertyResolveRequestFactory) {
        this.propertyResolveRequestFactory = propertyResolveRequestFactory;
    }

    public SelectionResult resolve(PropertyResolver propertyResolver, SelectionRequest request, Object leftValue) {
        if (leftValue == null || leftValue == Undefined.UNDEFINED)
            return new SelectionResult(Optional.<PropertyResolver>empty(), Optional.<Value>empty());
        Optional<Value> value = propertyResolver.resolve(propertyResolveRequestFactory.create(request, leftValue));
        return new SelectionResult(Optional.of(propertyResolver), value);
    }
}
