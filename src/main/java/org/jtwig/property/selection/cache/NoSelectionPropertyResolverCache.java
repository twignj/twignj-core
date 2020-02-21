package org.jtwig.property.selection.cache;

import java.util.Optional;
import org.jtwig.property.resolver.PropertyResolver;

public class NoSelectionPropertyResolverCache implements SelectionPropertyResolverCache {
    private static final NoSelectionPropertyResolverCache INSTANCE = new NoSelectionPropertyResolverCache();

    public static NoSelectionPropertyResolverCache noSelectionPropertyResolverCache () {
        return INSTANCE;
    }

    private NoSelectionPropertyResolverCache () {}

    @Override
    public Optional<PropertyResolver> getCachedResolver(SelectionPropertyResolverCacheKey cacheKey) {
        return Optional.empty();
    }

    @Override
    public void cacheResolver(SelectionPropertyResolverCacheKey cacheKey, PropertyResolver propertyResolver) {

    }
}
