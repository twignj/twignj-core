package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.util.Collections2;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MapSelectionTest extends AbstractIntegrationTest {
    @Test
    public void mapSelectionWithArray() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1, 2][0] }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void mapSelectionUndefined() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (defined([1, 2][5])) %}KO{% else %}OK{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }

    @Test
    public void mapSelectionOnVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (list[0]) %}KO{% else %}OK{% endif %}")
                .render(JtwigModel.newModel().with("list", Collections2.listOf(false)));

        assertThat(result, is("OK"));
    }

    @Test
    public void mapSelectionOnVariableWithPositionAsVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (list[position]) %}KO{% else %}OK{% endif %}")
                .render(JtwigModel.newModel()
                        .with("list", Collections2.listOf(false))
                        .with("position", 0)
                );

        assertThat(result, is("OK"));
    }

    @Test
    public void mapSelectionOnVariableWithPositionAsExpression() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (list[position.size]) %}KO{% else %}OK{% endif %}")
                .render(JtwigModel.newModel()
                        .with("list", Collections2.listOf(false))
                        .with("position", Collections2.listOf())
                );

        assertThat(result, is("OK"));
    }

    @Test
    public void mapSelectionOnBinaryExpressionWithPositionAsExpression() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (bean.get[position.size]) %}KO{% else %}OK{% endif %}")
                .render(JtwigModel.newModel()
                        .with("bean", new BeanTest<>(Collections2.listOf(false)))
                        .with("position", Collections2.listOf())
                );

        assertThat(result, is("OK"));
    }
    @Test
    public void mapSelectionOfMapSelection() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if ((bean.get[0])[position.size]) %}KO{% else %}OK{% endif %}")
                .render(JtwigModel.newModel()
                        .with("bean", new BeanTest<>(Collections2.listOf(Collections2.listOf(false))))
                        .with("position", Collections2.listOf())
                );

        assertThat(result, is("OK"));
    }

    public static class BeanTest<T> {
        private final T value;

        public BeanTest(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }
    }
}
