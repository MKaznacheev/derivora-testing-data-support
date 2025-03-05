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

package xyz.derivora.testing.data.support.aggregator;

import xyz.derivora.testing.data.support.supplier.DataSupplier;
import xyz.derivora.utilkit.arrays.ArrayGenerator;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;


/**
 * A {@code DataAggregator} implementation that flattens multiple {@link DataSupplier} outputs into a single array.
 *
 * <p>The {@code FlatDataAggregator} takes multiple suppliers of data and aggregates
 * their results into a single contiguous array. The aggregation is performed by
 * concatenating the arrays returned by the individual suppliers.</p>
 *
 * @param <T> the type of elements supplied and aggregated
 */
public final class FlatDataAggregator<T> implements DataAggregator<T> {

    /**
     * Generates arrays for storing the aggregated data.
     */
    private final ArrayGenerator<T> arrayGenerator;

    /**
     * Constructs a {@code FlatDataAggregator} with the specified array generator.
     *
     * <p>The provided {@link ArrayGenerator} is used to create arrays for the aggregated data.</p>
     *
     * @param arrayGenerator the generator used to create result arrays
     * @throws NullPointerException if {@code arrayGenerator} is {@code null}
     */
    public FlatDataAggregator(ArrayGenerator<T> arrayGenerator) {
        this.arrayGenerator = Objects.requireNonNull(arrayGenerator, "Array generator cannot be null");
    }

    /**
     * Aggregates data from multiple {@link DataSupplier} instances into a single array.
     *
     * <p>If no suppliers are provided, an empty array is returned. If only one supplier is provided,
     * its array is returned as a copy to prevent modifications to the original data.</p>
     *
     * <p>If multiple suppliers are provided, their arrays are concatenated in the order they
     * appear in the argument list.</p>
     *
     * @param suppliers the data suppliers providing elements to be aggregated
     * @return a single array containing all aggregated elements
     * @throws NullPointerException if {@code suppliers} or any individual supplier is {@code null}
     */
    @Override
    @SafeVarargs
    public final T[] aggregate(DataSupplier<T>... suppliers) {
        Objects.requireNonNull(suppliers, "Suppliers array cannot be null");

        for (int i = 0; i < suppliers.length; i++) {
            Objects.requireNonNull(suppliers[i], "Supplier at index " + i + " cannot be null");
        }

        return switch (suppliers.length) {
            case 0 -> arrayGenerator.generate(0);
            case 1 -> Arrays.copyOf(suppliers[0].get(), suppliers[0].get().length);
            default -> Stream.of(suppliers)
                             .map(DataSupplier::get)
                             .flatMap(Arrays::stream)
                             .toArray(arrayGenerator::generate);
        };
    }
}
