package org.jtwig.escape.environment;

import org.jtwig.escape.EscapeEngineSelector;
import org.jtwig.escape.config.EscapeEngineConfiguration;
import org.jtwig.util.Objects2;

public class EscapeEnvironmentFactory {
    public EscapeEnvironment create (EscapeEngineConfiguration configuration) {
        Objects2.require(!configuration.getEscapeEngineMap().containsKey(configuration.getInitialEngine()), String.format("Invalid initial escape mode %s, it must be one of %s", configuration.getInitialEngine(), configuration.getEscapeEngineMap().keySet()));
        Objects2.require(!configuration.getEscapeEngineMap().containsKey(configuration.getDefaultEngine()), String.format("Invalid default escape mode %s, it must be one of %s", configuration.getDefaultEngine(), configuration.getEscapeEngineMap().keySet()));

        return new EscapeEnvironment(
                configuration.getEscapeEngineMap().get(configuration.getInitialEngine()),
                configuration.getDefaultEngine(),
                EscapeEngineSelector.newInstance(configuration.getEscapeEngineMap())
        );
    }
}
