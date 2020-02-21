package org.jtwig.parser.cache;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.resource.reference.ResourceReference;

public class InMemoryConcurrentPersistentTemplateCache implements TemplateCache {
    private final ConcurrentMap<ResourceReference, Future<Result>> cache;
    private final Function<Future<Result>, Result> retriever = new RetrieveFuture<>();

    public InMemoryConcurrentPersistentTemplateCache () {
        this(101, Integer.MAX_VALUE);
    }
    public InMemoryConcurrentPersistentTemplateCache(int initialCapacity, int maxValue) {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public Node get(JtwigParser parser, Environment environment, ResourceReference resource) {
        Optional<Future<Result>> optional = Optional.ofNullable(cache.get(resource));
        if (optional.isPresent()) {
            return retriever.apply(optional.get()).get();
        } else {
            CompletableFuture<Result> future = new CompletableFuture<>();
            Future<Result> result = cache.putIfAbsent(resource, future);
            if (result == null) {
                try {
                    Node node = parser.parse(environment, resource);
                    future.complete(new Result(Optional.of(node), Optional.<RuntimeException>empty()));
                    return node;
                } catch (RuntimeException e) {
                    cache.remove(resource);
                    future.complete(new Result(Optional.<Node>empty(), Optional.of(e)));
                    throw e;
                }
            } else {
                return retriever.apply(result).get();
            }
        }
    }

    public static class Result {
        private final Optional<Node> node;
        private final Optional<RuntimeException> exception;


        public Result(Optional<Node> node, Optional<RuntimeException> exception) {
            this.node = node;
            this.exception = exception;
        }

        public Node get () {
            if (!node.isPresent()) {
                throw exception.get();
            } else {
                return node.get();
            }
        }
    }
}
