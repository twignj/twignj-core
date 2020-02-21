package org.jtwig.util.builder;

import java.util.Collections;
import java.util.function.Predicate;
import org.jtwig.environment.and.AndBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListBuilder<B, T> implements AndBuilder<B> {
    private final B parent;
    private List<T> list = new ArrayList<>();

    public ListBuilder(B parent) {
        this.parent = parent;
    }
    public ListBuilder(B parent, Collection<? extends T> list) {
        this.parent = parent;
        this.list = new ArrayList<>(list);
    }


    public ListBuilder<B, T> set (List<? extends T> items) {
        this.list = new ArrayList<>(items);
        return this;
    }

    public ListBuilder<B, T> add (T item) {
        this.list.add(item);
        return this;
    }

    public ListBuilder<B, T> add (List<? extends T> items) {
        this.list.addAll(items);
        return this;
    }

    public ListBuilder<B, T> filter (Predicate<T> predicate) {
        List<T> newList = new ArrayList<>();
        for (T item : this.list) {
            if (predicate.test(item)) {
                newList.add(item);
            }
        }
        this.list = newList;
        return this;
    }

    @Override
    public B and() {
        return parent;
    }

    public List<T> build() {
        return Collections.unmodifiableList(new ArrayList<>(this.list));
    }
}
