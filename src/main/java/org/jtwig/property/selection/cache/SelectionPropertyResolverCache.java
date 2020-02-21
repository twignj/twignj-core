package org.jtwig.property.selection.cache;

import java.util.Optional;
import org.jtwig.property.resolver.PropertyResolver;

public interface SelectionPropertyResolverCache {
    Optional<PropertyResolver> getCachedResolver(SelectionPropertyResolverCacheKey cacheKey);

    void cacheResolver(SelectionPropertyResolverCacheKey cacheKey, PropertyResolver propertyResolver);
}
