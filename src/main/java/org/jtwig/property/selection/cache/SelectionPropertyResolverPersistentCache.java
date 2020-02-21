package org.jtwig.property.selection.cache;

import java.util.Optional;
import org.jtwig.property.resolver.PropertyResolver;

import java.util.concurrent.ConcurrentHashMap;

public class SelectionPropertyResolverPersistentCache implements SelectionPropertyResolverCache {
    private final ConcurrentHashMap<SelectionPropertyResolverCacheKey, PropertyResolver> hashMap;

    public SelectionPropertyResolverPersistentCache(ConcurrentHashMap<SelectionPropertyResolverCacheKey, PropertyResolver> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public Optional<PropertyResolver> getCachedResolver(SelectionPropertyResolverCacheKey cacheKey) {
        return Optional.ofNullable(hashMap.get(cacheKey));
    }

    @Override
    public void cacheResolver(SelectionPropertyResolverCacheKey cacheKey, PropertyResolver propertyResolver) {
        hashMap.put(cacheKey, propertyResolver);
    }
}
