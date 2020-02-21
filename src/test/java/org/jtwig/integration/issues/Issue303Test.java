package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.util.Collections2.mapOf;
import static org.junit.Assert.assertThat;

public class Issue303Test {
    @Test
    public void retrievingUsingGenericGetMethod() throws Exception {
        JtwigModel model = JtwigModel.newModel().with("bean", new TestBean(
                mapOf("a", "Hi")
        ));

        String result = JtwigTemplate.inlineTemplate("{{ bean.a }}", configuration()
                .build())
                .render(model);

        assertThat(result, is("Hi"));
    }

    public static class TestBean {
        private final Map<String, String> values;

        public TestBean(Map<String, String> values) {
            this.values = values;
        }

        public String get (String key) {
            return values.get(key);
        }
    }
}
