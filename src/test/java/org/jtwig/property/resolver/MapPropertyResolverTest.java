package org.jtwig.property.resolver;

import java.util.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MapPropertyResolverTest {
    private MapPropertyResolver underTest = new MapPropertyResolver();

    @Test
    public void resolveNull() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(null);

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void resolveNonValueContext() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getContext()).willReturn(new Object());

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.empty(), result);
    }
}