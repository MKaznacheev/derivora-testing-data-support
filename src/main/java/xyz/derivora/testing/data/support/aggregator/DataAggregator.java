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

/**
 * Aggregates data from multiple {@link DataSupplier} instances into a single array.
 *
 * <p>The {@code DataAggregator} interface defines a mechanism for collecting and merging
 * data from multiple suppliers, producing a consolidated result.</p>
 *
 * @param <T> the type of data supplied and aggregated
 */
@FunctionalInterface
public interface DataAggregator<T> {

    /**
     * Aggregates data from the provided {@link DataSupplier} instances.
     *
     * <p>Each supplier contributes an array of elements, which are combined into a single
     * resulting array. The exact aggregation strategy depends on the implementation.</p>
     *
     * @param suppliers the data suppliers providing elements to be aggregated
     * @return an array containing all aggregated elements
     * @throws NullPointerException if {@code suppliers} or any of its elements is {@code null}
     */
    T[] aggregate(DataSupplier<T>... suppliers);
}
