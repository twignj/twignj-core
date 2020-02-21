package org.jtwig.value.convert.collection;

import org.jtwig.util.Collections2;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MapToCollectionConverterTest {
    private MapToCollectionConverter underTest = new MapToCollectionConverter();

    @Test
    public void convertNotMap() throws Exception {
        Object object = new Object();

        Converter.Result<WrappedCollection> result = underTest.convert(object);

        assertEquals(false, result.isDefined());
    }

    @Test
    public void convertMap() throws Exception {
        Object firstValue = "b";
        Object lastValue = "d";
        var map = Collections2
                .map("a", firstValue)
                .put("c", lastValue)
                .build();

        Converter.Result<WrappedCollection> result = underTest.convert(map);

        WrappedCollection entries = result.get();

        assertSame(firstValue, entries.getValue("a"));
        assertSame(lastValue, entries.getValue("c"));
    }
}