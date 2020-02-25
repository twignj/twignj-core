package org.jtwig.functions.impl.mixed;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.Collections2;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.*;

public class ReverseFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "reverse";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1).maximumNumberOfArguments(1);

        Object input = request.get(0);
        Converter.Result<WrappedCollection> collectionResult = request.getEnvironment()
                .getValueEnvironment().getCollectionConverter()
                .convert(input);

        if (collectionResult.isDefined()) {
            return reverse(collectionResult.get().values());
        } else if (input instanceof String) {
            return StringUtils.reverse((String) input);
        } else {
            return input;
        }
    }

    private <T> Collection<T> reverse(Collection<T> values) {
        final List<T> list = new ArrayList(values);
        Collections.reverse(list);
        return Collections2.unmodifableList(list);
    }

}
