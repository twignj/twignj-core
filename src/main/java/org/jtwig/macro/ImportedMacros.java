package org.jtwig.macro;

import java.util.Optional;

import java.util.Map;

public class ImportedMacros {
    private final Map<String, Macro> macros;

    public ImportedMacros(Map<String, Macro> macros) {
        this.macros = macros;
    }

    public Optional<Macro> resolve(String name) {
        return Optional.ofNullable(macros.get(name));
    }
}
