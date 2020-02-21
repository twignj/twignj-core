package org.jtwig.functions.impl.java;

import java.util.Optional;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.reflection.model.java.JavaConstant;
import org.jtwig.util.ClasspathFinder;

public class ConstantFunction extends SimpleJtwigFunction {
    private final ClasspathFinder classpathFinder;

    public ConstantFunction(ClasspathFinder classpathFinder) {
        this.classpathFinder = classpathFinder;
    }

    @Override
    public String name() {
        return "constant";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request
                .minimumNumberOfArguments(1)
                .maximumNumberOfArguments(2);

        if (request.getNumberOfArguments() == 1) {
            return getConstant(request, getString(request, 0));
        } else {

            Object constantValue = getConstant(request, getString(request, 1));
            return request.getEnvironment()
                    .getValueEnvironment().getValueComparator()
                    .compare(request, constantValue, request.get(0)) == 0;
        }
    }


    private String getString(FunctionRequest request, int index) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(index));
    }

    private Object getConstant(FunctionRequest request, String constant) {
        int constantNamePosition = constant.lastIndexOf(".");
        if (constantNamePosition == -1) {
            throw request.exception(String.format("Invalid constant specified '%s'", constant));
        }

        String className = constant.substring(0, constantNamePosition);
        String constantName = constant.substring(constantNamePosition + 1);

        Optional<JavaClass> optional = classpathFinder.load(className);
        if (optional.isPresent()) {
            Optional<JavaConstant> valueOptional = optional.get().constant(constantName);
            if (valueOptional.isPresent()) {
                Optional<Value> value = valueOptional.get().value();
                if (value.isPresent()) {
                    return value.get().getValue();
                } else {
                    throw request.exception(String.format("Unable to retrieve value of constant %s in class %s", constantName, className));
                }
            } else {
                throw request.exception(String.format("Class %s does not expose constant %s", className, constantName));
            }
        } else {
            throw request.exception(String.format("Class %s not found", className));
        }
    }
}
