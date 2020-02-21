package org.jtwig.resource.resolver;

import java.util.Optional;
import org.jtwig.resource.reference.ResourceReference;

public interface RelativeResourceResolver {
    Optional<ResourceReference> resolve(ResourceReference parentReference, ResourceReference newPath);
}
