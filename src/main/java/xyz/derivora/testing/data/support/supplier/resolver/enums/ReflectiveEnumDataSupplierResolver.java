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
 * A {@link EnumDataSupplierResolver} implementation that resolves {@link DataSupplier} instances using enum constants.
 *
 * <p>This class retrieves {@link DataSupplier} instances from an enumeration type, assuming that
 * the enum itself implements {@link DataSupplier}.</p>
 *
 * <p>It extends {@link AbstractEnumDataSupplierResolver}, providing a concrete implementation
 * of the {@link #resolveConstants(Class)} method.</p>
 *
 * <p>Instances of this resolver are typically obtained through {@link EnumDataSupplierResolver#getInstance(ArrayGenerator)}.</p>
 *
 * @param <T> the type of elements supplied by the resolved {@link DataSupplier} instances
 */
final class ReflectiveEnumDataSupplierResolver<T> extends AbstractEnumDataSupplierResolver<T> {

    /**
     * Constructs a {@code ReflectiveEnumDataSupplierResolver} with the specified array generator.
     *
     * @param arrayGenerator the generator used to create arrays for resolved suppliers
     * @throws NullPointerException if {@code arrayGenerator} is {@code null}
     */
    ReflectiveEnumDataSupplierResolver(ArrayGenerator<DataSupplier<T>> arrayGenerator) {
        super(arrayGenerator);
    }

    /**
     * Resolves all {@link DataSupplier} constants from the specified enumeration type.
     *
     * <p>This method retrieves an array of all enum constants defined in the given
     * {@code enumType}, assuming that the enum implements {@link DataSupplier}.</p>
     *
     * <p>If the provided class is not an enumeration or does not define any constants,
     * a {@link IllegalArgumentException} is thrown.</p>
     *
     * @param enumType the enumeration class containing {@link DataSupplier} constants
     * @return an array of resolved {@link DataSupplier} instances
     * @throws IllegalArgumentException if the specified class is not an enum or does not define any constants
     */
    @Override
    protected DataSupplier<T>[] resolveConstants(Class<? extends DataSupplier<T>> enumType) {
        DataSupplier<T>[] constants = enumType.getEnumConstants();

        if (constants == null) {
            throw new IllegalArgumentException("The provided class " + enumType.getName() +
                                                       " is not a valid enum type or does not contain any constants.");
        }

        return constants;
    }
}
