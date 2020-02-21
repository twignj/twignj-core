package org.jtwig.property.selection;

import java.util.Optional;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.selection.cache.SelectionPropertyResolverCache;
import org.jtwig.property.selection.cache.SelectionPropertyResolverCacheKey;

public class CachedSelectionPropertyResolver implements SelectionPropertyResolver {
    private final SelectionPropertyResolverCache selectionPropertyResolverCache;
    private final SelectionPropertyResolver delegate;
    private final SelectionPropertyResolveService selectionPropertyResolveService;

    public CachedSelectionPropertyResolver(SelectionPropertyResolverCache selectionPropertyResolverCache, SelectionPropertyResolver delegate, SelectionPropertyResolveService selectionPropertyResolveService) {
        this.selectionPropertyResolverCache = selectionPropertyResolverCache;
        this.delegate = delegate;
        this.selectionPropertyResolveService = selectionPropertyResolveService;
    }

    @Override
    public SelectionResult resolve(SelectionRequest request) {
        Object leftValue = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService()
                .calculate(request, request.getLeftExpression());

        SelectionPropertyResolverCacheKey cacheKey = SelectionPropertyResolverCacheKey.createFor(leftValue, request.getRightExpression());

        Optional<PropertyResolver> result = selectionPropertyResolverCache.getCachedResolver(cacheKey);
        if (result.isPresent()) {
            PropertyResolver propertyResolver = result.get();
            return selectionPropertyResolveService.resolve(propertyResolver, request, leftValue);
        } else {
            SelectionResult cacheMissResult = delegate.resolve(request);
            if (cacheMissResult.getPropertyResolver().isPresent()) {
                selectionPropertyResolverCache.cacheResolver(cacheKey, cacheMissResult.getPropertyResolver().get());
            }
            return cacheMissResult;
        }
    }
}
