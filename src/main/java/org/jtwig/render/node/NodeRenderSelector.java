package org.jtwig.render.node;

import java.util.Optional;
import org.jtwig.model.tree.Node;
import org.jtwig.render.node.renderer.NodeRender;

import java.util.Map;

public class NodeRenderSelector {
    private final Map<Class<? extends Node>, NodeRender> nodeRenderMap;

    public NodeRenderSelector(Map<Class<? extends Node>, NodeRender> nodeRenderMap) {
        this.nodeRenderMap = nodeRenderMap;
    }

    public Optional<NodeRender> renderFor(Node node) {
        return Optional.ofNullable(nodeRenderMap.get(node.getClass()));
    }
}
