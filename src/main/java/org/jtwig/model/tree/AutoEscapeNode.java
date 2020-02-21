package org.jtwig.model.tree;

import java.util.Optional;
import org.jtwig.model.position.Position;

public class AutoEscapeNode extends ContentNode {
    private final Optional<String> escapeEngineName;

    public AutoEscapeNode(Position position, Node content, Optional<String> escapeEngineName) {
        super(position, content);
        this.escapeEngineName = escapeEngineName;
    }

    public Optional<String> getEscapeEngineName() {
        return escapeEngineName;
    }
}
