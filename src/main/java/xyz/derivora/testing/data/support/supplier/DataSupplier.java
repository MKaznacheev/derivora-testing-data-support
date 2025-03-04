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

package xyz.derivora.testing.data.support.supplier;

import java.util.function.Supplier;

/**
 * A functional interface for supplying arrays of data.
 *
 * <p>This interface extends {@link Supplier} with a more specific contract:
 * it provides an array of elements of type {@code T} instead of a single instance.
 * This is useful when working with batch processing, testing data generation, or
 * structured data retrieval.</p>
 *
 * @param <T> the type of elements in the supplied array
 */
@FunctionalInterface
public interface DataSupplier<T> extends Supplier<T[]> {
}
