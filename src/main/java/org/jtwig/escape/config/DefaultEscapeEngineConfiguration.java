package org.jtwig.escape.config;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.escape.JavascriptEscapeEngine;
import org.jtwig.escape.NoneEscapeEngine;
import org.jtwig.util.Collections2;

public class DefaultEscapeEngineConfiguration extends EscapeEngineConfiguration {
    public DefaultEscapeEngineConfiguration() {
        super(
                "none",
                "html",
                Collections2.
                        <String, EscapeEngine>map("none", NoneEscapeEngine.instance())
                        .put("html", HtmlEscapeEngine.instance())
                        .put("js", JavascriptEscapeEngine.instance())
                        .put("javascript", JavascriptEscapeEngine.instance())
                        .build());
    }
}
