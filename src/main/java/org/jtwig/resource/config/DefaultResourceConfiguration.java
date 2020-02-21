package org.jtwig.resource.config;

import org.jtwig.resource.loader.ClasspathResourceLoader;
import org.jtwig.resource.loader.FileResourceLoader;
import org.jtwig.resource.loader.StringResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.reference.DefaultResourceReferenceExtractor;
import org.jtwig.resource.reference.PosixResourceReferenceExtractor;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.reference.UncResourceReferenceExtractor;
import org.jtwig.resource.reference.path.PathTypeSupplier;
import org.jtwig.resource.resolver.ReferenceRelativeResourceResolver;
import org.jtwig.resource.resolver.RelativeResourceResolver;
import org.jtwig.resource.resolver.path.RelativeFilePathResolver;
import org.jtwig.resource.resolver.path.RelativePathResolver;
import org.jtwig.util.Collections2;

import java.nio.charset.Charset;

import static java.util.Collections.singleton;

public class DefaultResourceConfiguration extends ResourceConfiguration {
    public DefaultResourceConfiguration() {
        super(Collections2.<RelativeResourceResolver>list()
                .add(new ReferenceRelativeResourceResolver(singleton(ResourceReference.CLASSPATH), RelativePathResolver.instance()))
                .add(new ReferenceRelativeResourceResolver(singleton(ResourceReference.FILE), RelativeFilePathResolver.instance()))
                .build(),
                Collections2.listOf(ResourceReference.STRING, ResourceReference.MEMORY),
                Collections2.listOf(
                        new TypedResourceLoader(ResourceReference.FILE, FileResourceLoader.instance()),
                        new TypedResourceLoader(ResourceReference.CLASSPATH, new ClasspathResourceLoader(DefaultResourceConfiguration.class.getClassLoader())),
                        new TypedResourceLoader(ResourceReference.STRING, StringResourceLoader.instance())
                ),
                new DefaultResourceReferenceExtractor(PathTypeSupplier.pathTypeSupplier(), new PosixResourceReferenceExtractor(), new UncResourceReferenceExtractor()),
                Charset.defaultCharset());
    }
}
