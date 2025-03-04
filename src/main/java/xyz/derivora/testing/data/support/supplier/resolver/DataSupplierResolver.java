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

package xyz.derivora.testing.data.support.supplier.resolver;

import xyz.derivora.testing.data.support.supplier.DataSupplier;
import xyz.derivora.utilkit.arrays.ArrayGenerator;

/**
 * Resolves {@link DataSupplier} instances from their corresponding class references.
 *
 * <p>This interface provides a mechanism for dynamically creating instances of
 * {@link DataSupplier} based on their class definitions.</p>
 *
 * <p>By default, the {@link #getInstance(ArrayGenerator)} method returns a
 * resolver that uses reflection to instantiate suppliers.</p>
 *
 * @param <T> the type of elements supplied by the resolved {@link DataSupplier} instances
 */
public interface DataSupplierResolver<T> {

    /**
     * Resolves an array of {@link DataSupplier} instances from the provided class references.
     *
     * @param supplierClasses the classes of the {@link DataSupplier} implementations to be instantiated
     * @return an array of resolved {@link DataSupplier} instances
     * @throws Exception if instantiation fails due to an invalid class definition or a reflection error
     */
    DataSupplier<T>[] resolve(Class<? extends DataSupplier<T>>... supplierClasses) throws Exception;

    /**
     * Returns a default instance of {@code DataSupplierResolver} that uses reflection
     * to instantiate {@link DataSupplier} instances.
     *
     * @param arrayGenerator the generator used to create arrays of {@link DataSupplier}
     * @param <T> the type of elements supplied by the resolved instances
     * @return a {@code DataSupplierResolver} implementation
     */
    static <T> DataSupplierResolver<T> getInstance(ArrayGenerator<DataSupplier<T>> arrayGenerator) {
        return new ReflectiveDataSupplierResolver<>(arrayGenerator);
    }
}
