package org.jtwig;

import java.util.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.util.Collections2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JtwigModelTest {
    @Test
    public void newModelFromMap() throws Exception {

        JtwigModel underTest = JtwigModel.newModel(Collections2.mapOf("test", "hello"));

        Optional<Value> result = underTest.get("test");

        assertEquals("hello", result.get().getValue());
    }
}