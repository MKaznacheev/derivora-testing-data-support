/*
 * This file is part of Derivora Testing Data Support.
 *
 * Derivora Testing Data Support is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Derivora Testing Data Support is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Derivora Testing Data Support. If not, see https://www.gnu.org/licenses/lgpl-3.0.html.
 */

package xyz.derivora.testing.data.support.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * A base class for providing test data to parameterized JUnit tests using annotations.
 *
 * <p>This abstract class serves as a foundation for creating custom JUnit {@link ArgumentsProvider}
 * implementations that extract test parameters from annotations.</p>
 *
 * <p>Subclasses must define how to resolve data from an annotation by implementing
 * {@link #resolveData(ExtensionContext, Annotation)}.</p>
 *
 * @param <A> the annotation type used to extract test data
 */
public abstract class DataProvider<A extends Annotation> implements ArgumentsProvider {

    /**
     * The annotation type that this data provider processes.
     */
    private final Class<A> annotationType;

    /**
     * Constructs a {@code DataProvider} with the specified annotation type.
     *
     * <p>The provided annotation type is used to locate and process annotations
     * in the JUnit {@link ExtensionContext}.</p>
     *
     * @param annotationType the class type of the annotation to process
     * @throws NullPointerException if {@code annotationType} is {@code null}
     */
    protected DataProvider(Class<A> annotationType) {
        this.annotationType = Objects.requireNonNull(annotationType, "Annotation type cannot be null");
    }

    /**
     * Provides a stream of arguments for parameterized tests based on annotations found in the test context.
     *
     * <p>This method retrieves all annotations of the specified type from the {@link ExtensionContext}
     * and resolves data for each annotation.</p>
     *
     * <p>If no relevant annotations are found, an empty stream is returned. Each resolved data set
     * is flattened into individual arguments.</p>
     *
     * @param context the JUnit {@link ExtensionContext} providing test execution details
     * @return a {@link Stream} of {@link Arguments} representing test parameters
     * @throws Exception if an error occurs while resolving data
     */
    @Override
    public final Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        A[] annotations = resolveAnnotations(context);
        if (annotations.length == 0) {
            return Stream.empty();
        }

        Stream.Builder<Object[]> dataBuilder = Stream.builder();
        for (A annotation : annotations) {
            Object[] data = resolveData(context, annotation);
            dataBuilder.accept(data);
        }

        return dataBuilder.build().flatMap(Arrays::stream).map(DataProvider::createArguments);
    }

    /**
     * Resolves test data based on the given annotation.
     *
     * <p>This method must be implemented by subclasses to define how data should be extracted
     * from an annotation and transformed into an array of objects suitable for parameterized testing.</p>
     *
     * <p>If the annotation provides or references a collection of values where each value
     * should be passed as the same argument in separate test invocations,
     * this method should return a single-dimensional array.</p>
     *
     * <p>If the annotation provides or references a collection of value sets, where each
     * set corresponds to multiple arguments within a single test invocation,
     * this method should return a two-dimensional array.</p>
     *
     * <p>This ensures that each test case receives the correct number of arguments.</p>
     *
     * @param context the JUnit {@link ExtensionContext} providing test execution details
     * @param annotation the annotation instance containing test data
     * @return an array of objects representing test parameters; if multiple argument sets are needed, return an array of arrays
     * @throws Exception if data resolution fails
     */
    protected abstract Object[] resolveData(ExtensionContext context, A annotation) throws Exception;

    /**
     * Wraps the provided object into a JUnit {@link Arguments} instance.
     *
     * <p>This method ensures that if the provided object is an array, its elements are properly
     * extracted and passed as separate arguments to JUnit's parameterized test framework.</p>
     *
     * <p>Primitive arrays (e.g., {@code int[]}, {@code double[]}) are automatically converted
     * to an equivalent {@code Object[]} representation to prevent nested array issues in JUnit.</p>
     *
     * @param object the object to be wrapped as test arguments
     * @return an {@link Arguments} instance containing the extracted values
     */
    private static Arguments createArguments(Object object) {
        if (object instanceof Object[] array) {
            return arguments(array);
        }

        if (object.getClass().isArray()) {
            int length = Array.getLength(object);
            Object[] unboxedArray = new Object[length];

            for (int i = 0; i < length; i++) {
                unboxedArray[i] = Array.get(object, i);
            }

            return arguments(unboxedArray);
        }

        return arguments(object);
    }

    /**
     * Resolves annotations of the specified type from the given {@link ExtensionContext}.
     *
     * <p>This method retrieves the element from the provided {@code context} and extracts
     * all annotations of type {@code A}. If no element is present, an exception is thrown.</p>
     *
     * @param context the JUnit {@link ExtensionContext} from which annotations are retrieved
     * @return an array of annotations of type {@code A}
     * @throws IllegalStateException if the element is not present in the context
     */
    private A[] resolveAnnotations(ExtensionContext context) {
        return context.getElement()
                      .map(this::extractAnnotations)
                      .orElseThrow(this::generateAnnotationNotFoundException);
    }

    /**
     * Extracts all annotations of the specified type from the given {@link AnnotatedElement}.
     *
     * @param element the element from which annotations are extracted
     * @return an array of annotations of type {@code A}
     */
    private A[] extractAnnotations(AnnotatedElement element) {
        return element.getAnnotationsByType(annotationType);
    }

    /**
     * Generates an {@link IllegalStateException} when the required annotation is not found.
     *
     * @return an {@code IllegalStateException} indicating that the annotation was not found
     */
    private IllegalStateException generateAnnotationNotFoundException() {
        String message = annotationType.getName() + " is required but not found";
        return new IllegalStateException(message);
    }
}
