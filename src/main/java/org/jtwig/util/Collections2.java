package org.jtwig.util;

import org.jtwig.resource.loader.TypedResourceLoader;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Collections2 {
    public static <E> List<E> unmodifableList(List<E> expressions) {
        return Collections.unmodifiableList(new ArrayList<>(expressions));
    }

    public static <K, V> Map<K,V> unmodifableMap(Map<K, V> map) {
        return Collections.unmodifiableMap(new HashMap<>(map));
    }

    public static <K, V> MapBuilder<K,V> map() {
        return new MapBuilder();
    }

    public static <K, V> MapBuilder<K,V> map(K key, V value) {
        return new MapBuilder().put(key, value);
    }

    public static <K, V> Map<K,V> mapOf(K key, V value) {
        return map(key, value).build();
    }

    public static <K, V> Map<K, List<V>> multimap(Collection<V> values, Function<V, K> key) {
        final Map<K, List<V>> v = new LinkedHashMap<>();
        values.forEach(it -> {
            K k = key.apply(it);
            final List<V> list;
            if (!v.containsKey(k)) {
                v.put(k, list = new ArrayList<>());
            } else {
                list = v.get(k);
            }
            list.add(it);
        });
        return v.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Collections2.unmodifableList(e.getValue())));
    }

    public static <B> ListBuilder<B> list() {
        return new ListBuilder();
    }

    public static <B> ListBuilder<B> list(B value) {
        return new ListBuilder().add(value);
    }

    public static <B> List<B> listOf(B... values) {
        return Collections.unmodifiableList(Arrays.asList(values));
    }


    public static class MapBuilder<K, V> {
        private Map<K, V> map = new HashMap();

        public MapBuilder<K, V> put(K key, V value) {
            map.put(key, value);
            return this;
        }

        public Map<K, V> build() {
            return unmodifableMap(map);
        }
    }

    /**
     * Returns a collection that applies {@code function} to each element of {@code fromCollection}.
     * The returned collection is a live view of {@code fromCollection}; changes to one affect the
     * other.
     *
     * <p>The returned collection's {@code add()} and {@code addAll()} methods throw an {@link
     * UnsupportedOperationException}. All other collection methods are supported, as long as {@code
     * fromCollection} supports them.
     *
     * <p>The returned collection isn't threadsafe or serializable, even if {@code fromCollection} is.
     *
     * <p>When a live view is <i>not</i> needed, it may be faster to copy the transformed collection
     * and use the copy.
     *
     * <p>If the input {@code Collection} is known to be a {@code List}, consider {@link
     * Collections2#transform}. If only an {@code Iterable} is available, use {@link Collections2#transform}.
     *
     * <p><b>{@code Stream} equivalent:</b> {@link java.util.stream.Stream#map Stream.map}.
     */
    public static <F, T> Collection<T> transform(
            Collection<F> fromCollection, Function<? super F, T> function) {
        return new TransformedCollection<>(fromCollection, function);
    }

    static class TransformedCollection<F, T> extends AbstractCollection<T> {
        final Collection<F> fromCollection;
        final Function<? super F, ? extends T> function;

        TransformedCollection(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
            this.fromCollection = requireNonNull(fromCollection, "fromCollection");
            this.function = requireNonNull(function, "function");
        }

        @Override
        public void clear() {
            fromCollection.clear();
        }

        @Override
        public boolean isEmpty() {
            return fromCollection.isEmpty();
        }

        @Override
        public Iterator<T> iterator() {
            return transform(fromCollection.iterator(), function);
        }


        @Override
        public Spliterator<T> spliterator() {
            return transform(fromCollection.spliterator(), function);
        }

        @Override
        public void forEach(Consumer<? super T> action) {
            requireNonNull(action);
            fromCollection.forEach((F f) -> action.accept(function.apply(f)));
        }

        @Override
        public boolean removeIf(java.util.function.Predicate<? super T> filter) {
            requireNonNull(filter);
            return fromCollection.removeIf(element -> filter.test(function.apply(element)));
        }

        @Override
        public int size() {
            return fromCollection.size();
        }
    }

    /**
     * Returns a view containing the result of applying {@code function} to each element of {@code
     * fromIterator}.
     *
     * <p>The returned iterator supports {@code remove()} if {@code fromIterator} does. After a
     * successful {@code remove()} call, {@code fromIterator} no longer contains the corresponding
     * element.
     */
    public static <F, T> Iterator<T> transform(
            final Iterator<F> fromIterator, final Function<? super F, ? extends T> function)
    {
        requireNonNull(function);
        return new TransformedIterator<F, T>(fromIterator) {
            @Override
            T transform(F from) {
                return function.apply(from);
            }
        };
    }

    abstract static class TransformedIterator<F, T> implements Iterator<T> {
        final Iterator<? extends F> backingIterator;

        TransformedIterator(Iterator<? extends F> backingIterator) {
            this.backingIterator = requireNonNull(backingIterator);
        }

        abstract T transform(F from);

        @Override
        public final boolean hasNext() {
            return backingIterator.hasNext();
        }

        @Override
        public final T next() {
            return transform(backingIterator.next());
        }

        @Override
        public final void remove() {
            backingIterator.remove();
        }
    }

    /**
     * Returns a {@code Spliterator} over the elements of {@code fromSpliterator} mapped by {@code
     * function}.
     */
    static <InElementT, OutElementT> Spliterator<OutElementT> transform(
            final Spliterator<InElementT> fromSpliterator,
            final Function<? super InElementT, ? extends OutElementT> function
    ) {
        requireNonNull(fromSpliterator);
        requireNonNull(function);
        return new Spliterator<OutElementT>() {

            @Override
            public boolean tryAdvance(Consumer<? super OutElementT> action) {
                return fromSpliterator.tryAdvance(
                        fromElement -> action.accept(function.apply(fromElement)));
            }

            @Override
            public void forEachRemaining(Consumer<? super OutElementT> action) {
                fromSpliterator.forEachRemaining(fromElement -> action.accept(function.apply(fromElement)));
            }

            @Override
            public Spliterator<OutElementT> trySplit() {
                Spliterator<InElementT> fromSplit = fromSpliterator.trySplit();
                return (fromSplit != null) ? transform(fromSplit, function) : null;
            }

            @Override
            public long estimateSize() {
                return fromSpliterator.estimateSize();
            }

            @Override
            public int characteristics() {
                return fromSpliterator.characteristics()
                        & ~(Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.SORTED);
            }
        };
    }

    public static class ListBuilder<B> {
        private List<B> list = new ArrayList<>();

        public ListBuilder<B> add(B value) {
            this.list.add(value);
            return this;
        }

        public List<B> build() {
            return Collections.unmodifiableList(this.list);
        }
    }
}
