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
import xyz.derivora.testing.data.support.aggregator.DataAggregator;
import xyz.derivora.testing.data.support.supplier.DataSupplier;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * A base class for providing test data by directly aggregating {@link DataSupplier} instances.
 *
 * <p>This abstract class extends {@link DataProvider} and integrates a {@link DataAggregator}
 * to combine multiple {@link DataSupplier} sources into a single dataset for parameterized tests.</p>
 *
 * @param <T> the type of data elements provided to the test
 * @param <A> the annotation type used to retrieve data supplier references
 */
public abstract class DirectDataProvider<T, A extends Annotation> extends DataProvider<A> {

    /**
     * Aggregates data from multiple {@link DataSupplier} instances.
     */
    private final DataAggregator<T> aggregator;

    /**
     * Constructs a {@code DirectDataProvider} with the specified annotation type and data aggregator.
     *
     * <p>The annotation type is used to locate and process relevant test data annotations.
     * The aggregator is responsible for combining data from multiple {@link DataSupplier} instances.</p>
     *
     * @param annotationType the class type of the annotation to process
     * @param aggregator the data aggregator used to merge results from multiple suppliers
     * @throws NullPointerException if {@code aggregator} is {@code null}
     */
    protected DirectDataProvider(Class<A> annotationType, DataAggregator<T> aggregator) {
        super(annotationType);
        this.aggregator = Objects.requireNonNull(aggregator, "Data aggregator cannot be null");
    }

    /**
     * Resolves data for parameterized tests by aggregating values from multiple {@link DataSupplier} instances.
     *
     * @param context the JUnit {@link ExtensionContext} providing test execution details
     * @param annotation the annotation instance containing supplier references
     * @return an array of aggregated test parameters
     * @throws Exception if resolving suppliers or aggregating data fails
     */
    @Override
    protected final T[] resolveData(ExtensionContext context, A annotation) throws Exception {
        DataSupplier<T>[] suppliers = resolveSuppliers(annotation);
        return aggregator.aggregate(suppliers);
    }

    /**
     * Resolves an array of {@link DataSupplier} instances from the provided annotation.
     *
     * <p>Subclasses must implement this method to specify how suppliers are obtained from
     * the annotation. Typically, this involves instantiating supplier classes
     * referenced in the annotation.</p>
     *
     * @param annotation the annotation instance containing references to {@link DataSupplier} implementations
     * @return an array of resolved {@link DataSupplier} instances
     * @throws Exception if supplier resolution fails
     */
    protected abstract DataSupplier<T>[] resolveSuppliers(A annotation) throws Exception;
}
