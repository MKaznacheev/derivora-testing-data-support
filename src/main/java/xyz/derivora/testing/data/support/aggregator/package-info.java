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

/**
 * Provides mechanisms for aggregating data from multiple {@link xyz.derivora.testing.data.support.supplier.DataSupplier} instances.
 *
 * <p>This package defines the {@link xyz.derivora.testing.data.support.aggregator.DataAggregator} interface,
 * which serves as a contract for classes that collect and combine data from multiple suppliers.
 * Implementations of this interface define different aggregation strategies.</p>
 *
 * <p>The main implementation provided is {@link xyz.derivora.testing.data.support.aggregator.FlatDataAggregator},
 * which concatenates the arrays supplied by individual {@link xyz.derivora.testing.data.support.supplier.DataSupplier}
 * instances into a single contiguous array.</p>
 */
package xyz.derivora.testing.data.support.aggregator;