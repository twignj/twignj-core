package org.jtwig.escape.config;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.util.builder.MapBuilder;

public class EscapeEngineConfigurationBuilder<B extends EscapeEngineConfigurationBuilder> {
    private String initialEngine;
    private String defaultEngine;
    private MapBuilder<B, String, EscapeEngine> escapeEngineMap;

    public EscapeEngineConfigurationBuilder(EscapeEngineConfiguration prototype) {
        this.escapeEngineMap = new MapBuilder<>(self(), prototype.getEscapeEngineMap());
        this.initialEngine = prototype.getInitialEngine();
        this.defaultEngine = prototype.getDefaultEngine();
    }

    public EscapeEngineConfigurationBuilder() {
        this.escapeEngineMap = new MapBuilder<>(self());
    }

    public B withInitialEngine(String initialEngine) {
        this.initialEngine = initialEngine;
        return self();
    }

    public B withDefaultEngine(String defaultEngine) {
        this.defaultEngine = defaultEngine;
        return self();
    }

    private B self() {
        return (B) this;
    }

    public MapBuilder<B, String, EscapeEngine> engines() {
        return escapeEngineMap;
    }

    public EscapeEngineConfiguration build() {
        return new EscapeEngineConfiguration(initialEngine, defaultEngine, escapeEngineMap.build());
    }
}
