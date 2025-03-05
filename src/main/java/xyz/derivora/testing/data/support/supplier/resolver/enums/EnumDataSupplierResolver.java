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

package xyz.derivora.testing.data.support.supplier.resolver.enums;

import xyz.derivora.testing.data.support.supplier.DataSupplier;
import xyz.derivora.utilkit.arrays.ArrayGenerator;

/**
 * Resolves {@link DataSupplier} instances from an enumeration type.
 *
 * <p>This interface provides a mechanism to map specific enumeration constants to
 * {@link DataSupplier} instances, allowing structured data to be retrieved based
 * on enum values.</p>
 *
 * <p>By default, the {@link #getInstance(ArrayGenerator)} method returns an implementation
 * that utilizes reflection to instantiate suppliers.</p>
 *
 * @param <T> the type of elements supplied by the resolved {@link DataSupplier} instances
 */
public interface EnumDataSupplierResolver<T> {

    /**
     * Resolves an array of {@link DataSupplier} instances from the specified enumeration type and constants.
     *
     * <p>Each provided enum constant name should correspond to a valid constant in the given
     * enumeration class. If any name does not match an existing enum constant, an exception is thrown.</p>
     *
     * @param enumType the enumeration class containing {@link DataSupplier} constants
     * @param constantNames the names of the enumeration constants to resolve
     * @return an array of resolved {@link DataSupplier} instances
     * @throws Exception if any of the provided enum constant names are invalid
     *                   or if instantiation fails due to a reflection error
     */
    DataSupplier<T>[] resolve(Class<? extends DataSupplier<T>> enumType, String... constantNames) throws Exception;

    /**
     * Returns a default instance of {@code EnumDataSupplierResolver} that uses reflection
     * to instantiate {@link DataSupplier} instances from an enumeration.
     *
     * @param arrayGenerator the generator used to create arrays of {@link DataSupplier}
     * @param <T> the type of elements supplied by the resolved instances
     * @return an {@code EnumDataSupplierResolver} implementation
     */
    static <T> EnumDataSupplierResolver<T> getInstance(ArrayGenerator<DataSupplier<T>> arrayGenerator) {
        return new ReflectiveEnumDataSupplierResolver<>(arrayGenerator);
    }
}
