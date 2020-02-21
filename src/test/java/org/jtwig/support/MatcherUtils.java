package org.jtwig.support;

import junit.framework.AssertionFailedError;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class MatcherUtils {
    public static <T> Matcher<? super T> theSameBean (T input) {
        return (Matcher<? super T>) theSame(input);
    }
    public static <T> Matcher<? extends T> theSame(final T input) {
        return new BaseMatcher<T>() {
            @Override
            public boolean matches(Object item) {
                try {
                    throw new IllegalStateException("reimplement ReflectionAssert.assertReflectionEquals(item, input); ");
                    // return true;
                } catch (AssertionFailedError e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(ToStringBuilder.reflectionToString(input));
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendValue(ToStringBuilder.reflectionToString(item));
            }
        };
    }
}
