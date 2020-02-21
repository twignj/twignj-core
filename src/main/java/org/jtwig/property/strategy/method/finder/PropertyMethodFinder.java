package org.jtwig.property.strategy.method.finder;

import java.util.Optional;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaMethod;

import java.util.List;

public interface PropertyMethodFinder {
    Optional<JavaMethod> find(JavaClass type, String identifier, List<Object> arguments);
}
