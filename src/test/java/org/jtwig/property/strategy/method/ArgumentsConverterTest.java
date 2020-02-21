package org.jtwig.property.strategy.method;

import java.util.Optional;
import org.jtwig.property.strategy.method.argument.group.ArgumentGroup;
import org.jtwig.property.strategy.method.argument.group.GroupingArgumentsService;
import org.jtwig.property.strategy.method.convert.Converter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ArgumentsConverterTest {
    private final Converter converter = mock(Converter.class);
    private final GroupingArgumentsService groupingArgumentsService = mock(GroupingArgumentsService.class);
    private ArgumentsConverter underTest = new ArgumentsConverter(converter, groupingArgumentsService);

    @Test
    public void unableToGroupArguments() throws Exception {
        JavaMethod javaMethod = mock(JavaMethod.class);
        Object[] objects = {};

        given(groupingArgumentsService.groupArguments(javaMethod, objects)).willReturn(Optional.empty());

        Optional<Object[]> result = underTest.convert(javaMethod, objects);


        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void argumentGroupUnableToConvert() throws Exception {
        Object[] objects = {};

        JavaMethod javaMethod = mock(JavaMethod.class);
        ArgumentGroup argumentGroup = mock(ArgumentGroup.class);

        given(groupingArgumentsService.groupArguments(javaMethod, objects)).willReturn(Optional.of(asList(argumentGroup)));
        given(argumentGroup.toArgument(converter)).willReturn(Optional.empty());

        Optional<Object[]> result = underTest.convert(javaMethod, objects);

        assertThat(result.isPresent(), is(false));
    }
}