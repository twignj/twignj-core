package org.jtwig.resource.loader;

import java.util.Optional;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class InMemoryResourceLoader implements ResourceLoader {
    public static InMemoryResourceLoader.Builder builder () {
        return new InMemoryResourceLoader.Builder();
    }

    private final Map<String, Supplier<InputStream>> inputStreamMap;

    public InMemoryResourceLoader(Map<String, Supplier<InputStream>> inputStreamMap) {
        this.inputStreamMap = inputStreamMap;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.empty();
    }

    @Override
    public InputStream load(String path) {
        Supplier<InputStream> streamSupplier = inputStreamMap.get(path);
        if (streamSupplier == null) throw new ResourceNotFoundException(String.format("Resource '%s' not found", path));
        return streamSupplier.get();
    }

    @Override
    public boolean exists(String path) {
        return inputStreamMap.containsKey(path);
    }

    @Override
    public Optional<URL> toUrl(String path) {
        return Optional.empty();
    }

    public static class Builder {
        private final Map<String, Supplier<InputStream>> supplierMap = new HashMap<>();

        public Builder withResource (String key, String content) {
            supplierMap.put(key, new StringInputStreamSupplier(content));
            return this;
        }

        public InMemoryResourceLoader build() {
            return new InMemoryResourceLoader(supplierMap);
        }
    }

    public static class StringInputStreamSupplier implements Supplier<InputStream> {
        private final String content;

        public StringInputStreamSupplier(String content) {
            this.content = content;
        }

        @Override
        public InputStream get() {
            return new ByteArrayInputStream(content.getBytes());
        }
    }
}
