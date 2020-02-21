package org.jtwig.functions.impl.mixed;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.util.Collections2;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.ArrayList;
import java.util.Collection;

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

    private Collection<Object> reverse(Collection<Object> values) {
        final ArrayList list = new ArrayList<>(values.size());
        int i = list.size();
        for (Object it : values) {
            list.set(i--, it);
        }
        return Collections2.unmodifableList(list);
    }

}
