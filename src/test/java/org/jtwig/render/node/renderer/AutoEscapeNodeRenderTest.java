package org.jtwig.render.node.renderer;

import java.util.Optional;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.escape.JavascriptEscapeEngine;
import org.jtwig.model.tree.AutoEscapeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.RenderException;
import org.jtwig.renderable.Renderable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AutoEscapeNodeRenderTest {
    private AutoEscapeNodeRender underTest = new AutoEscapeNodeRender();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderWhenEscapeModeNotProvided() throws Exception {
        EscapeEngine escapeEngine = HtmlEscapeEngine.instance();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        AutoEscapeNode node = mock(AutoEscapeNode.class);
        Node content = mock(Node.class);
        Renderable renderable = mock(Renderable.class);

        when(node.getContent()).thenReturn(content);
        when(node.getEscapeEngineName()).thenReturn(Optional.empty());
        when(request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().escapeEngineFor("default")).thenReturn(Optional.of(escapeEngine));
        when(request.getEnvironment().getEscapeEnvironment().getDefaultEscapeEngine()).thenReturn("default");
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);

        Renderable result = underTest.render(request, node);

        assertSame(renderable, result);
        verify(request.getRenderContext()).set(EscapeEngine.class, escapeEngine);
    }

    @Test
    public void renderWhenEscapeModeProvided() throws Exception {
        EscapeEngine escapeMode = JavascriptEscapeEngine.instance();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        AutoEscapeNode node = mock(AutoEscapeNode.class);
        Node content = mock(Node.class);
        Renderable renderable = mock(Renderable.class);

        when(node.getContent()).thenReturn(content);
        when(node.getEscapeEngineName()).thenReturn(Optional.of("js"));
        when(request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().escapeEngineFor("js")).thenReturn(Optional.of(escapeMode));
        when(request.getEnvironment().getEscapeEnvironment().getDefaultEscapeEngine()).thenReturn("default");
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);

        Renderable result = underTest.render(request, node);

        assertSame(renderable, result);
        verify(request.getRenderContext()).set(EscapeEngine.class, escapeMode);
    }

    @Test
    public void noEscapeEngine() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        AutoEscapeNode node = mock(AutoEscapeNode.class);
        Node content = mock(Node.class);
        Renderable renderable = mock(Renderable.class);

        when(node.getContent()).thenReturn(content);
        when(node.getEscapeEngineName()).thenReturn(Optional.of("js"));
        when(request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().escapeEngineFor("js")).thenReturn(Optional.empty());
        when(request.getEnvironment().getEscapeEnvironment().getDefaultEscapeEngine()).thenReturn("default");
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);

        expectedException.expectMessage("Invalid escape engine requested");
        expectedException.expect(RenderException.class);

        underTest.render(request, node);
    }
}