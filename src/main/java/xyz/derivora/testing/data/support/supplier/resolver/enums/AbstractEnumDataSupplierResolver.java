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

import java.util.Objects;

/**
 * An abstract base class for resolving {@link DataSupplier} instances from an enumeration type.
 *
 * <p>This class provides a framework for resolving {@link DataSupplier} constants
 * from an {@code enum} type and selecting specific constants by name.</p>
 *
 * <p>Subclasses must implement {@link #resolveConstants(Class)} to retrieve all available
 * {@link DataSupplier} constants from the given enumeration.</p>
 *
 * @param <T> the type of elements supplied by the resolved {@link DataSupplier} instances
 */
public abstract class AbstractEnumDataSupplierResolver<T> implements EnumDataSupplierResolver<T> {

    /**
     * Generates arrays for storing resolved {@link DataSupplier} instances.
     */
    private final ArrayGenerator<DataSupplier<T>> arrayGenerator;

    /**
     * Constructs an {@code AbstractEnumDataSupplierResolver} with the specified array generator.
     *
     * <p>The provided {@link ArrayGenerator} is used to create arrays of resolved
     * {@link DataSupplier} instances.</p>
     *
     * @param arrayGenerator the generator used to create arrays for resolved suppliers
     * @throws NullPointerException if {@code arrayGenerator} is {@code null}
     */
    public AbstractEnumDataSupplierResolver(ArrayGenerator<DataSupplier<T>> arrayGenerator) {
        this.arrayGenerator = Objects.requireNonNull(arrayGenerator, "Array generator cannot be null");
    }

    /**
     * Resolves an array of {@link DataSupplier} instances from the specified enumeration type and constants.
     *
     * <p>If no constant names are provided, an empty array is returned.</p>
     *
     * <p>Both {@code enumType} and {@code constantsNames} must not be {@code null}, and each
     * constant name must be valid. If any of these conditions are violated, an exception is thrown.</p>
     *
     * @param enumType the enumeration class containing {@link DataSupplier} constants
     * @param constantsNames the names of the enumeration constants to resolve
     * @return an array of resolved {@link DataSupplier} instances
     * @throws NullPointerException if {@code enumType} or {@code constantsNames} is {@code null},
     *         or if any element in {@code constantsNames} is {@code null}
     * @throws IllegalArgumentException if any of the provided constant names do not exist in the enumeration
     * @throws Exception if any of the provided enum constant names are invalid
     *                   or if instantiation fails due to a reflection error
     */
    @Override
    public final DataSupplier<T>[] resolve(Class<? extends DataSupplier<T>> enumType, String... constantsNames) throws Exception {
        Objects.requireNonNull(enumType, "Enum type cannot be null");
        Objects.requireNonNull(constantsNames, "Constants names array cannot be null");

        DataSupplier<T>[] constants = resolveConstants(enumType);

        if (constantsNames.length == 0) {
            return arrayGenerator.generate(0);
        }

        DataSupplier<T>[] suppliers = arrayGenerator.generate(constantsNames.length);
        for (int i = 0; i < suppliers.length; i++) {
            String constantName = Objects.requireNonNull(constantsNames[i], "Constant name cannot be null");
            suppliers[i] = resolve(constants, constantName);
        }

        return suppliers;
    }

    /**
     * Resolves all {@link DataSupplier} constants from the specified enumeration type.
     *
     * <p>This method retrieves an array of all enum constants defined in the given
     * {@code enumType}, assuming that the enum implements {@link DataSupplier}.</p>
     *
     * @param enumType the enumeration class containing {@link DataSupplier} constants
     * @return an array of resolved {@link DataSupplier} instances
     * @throws Exception if an error occurs while retrieving the constants
     */
    protected abstract DataSupplier<T>[] resolveConstants(Class<? extends DataSupplier<T>> enumType) throws Exception;

    /**
     * Resolves a {@link DataSupplier} instance from the provided array of enum constants.
     *
     * <p>This method iterates through the given {@code constants} array and returns the
     * first instance whose name matches the specified {@code constantName}.</p>
     *
     * <p>If no matching constant is found, an {@link IllegalArgumentException} is thrown.</p>
     *
     * @param constants the array of {@link DataSupplier} instances, expected to be enum constants
     * @param constantName the name of the enum constant to resolve
     * @return the matching {@link DataSupplier} instance
     * @throws IllegalArgumentException if no constant with the specified name is found
     */
    private DataSupplier<T> resolve(DataSupplier<T>[] constants, String constantName) {
        for (DataSupplier<T> constant : constants) {
            if (isMatchingEnumConstant(constant, constantName)) {
                return constant;
            }
        }

        throw new IllegalArgumentException("No such constant: " + constantName);
    }

    /**
     * Checks if the given {@link DataSupplier} instance matches the specified enum constant name.
     *
     * <p>This method assumes that the provided {@code constant} is an instance of an {@code enum}
     * and compares its {@link Enum#name()} with the given {@code constantName}.</p>
     *
     * @param constant the {@link DataSupplier} instance, expected to be an {@code enum} constant
     * @param constantName the expected name of the enum constant
     * @return {@code true} if the {@code constant} has the specified name, {@code false} otherwise
     * @throws ClassCastException if {@code constant} is not an instance of {@code Enum}
     */
    private boolean isMatchingEnumConstant(DataSupplier<T> constant, String constantName) {
        return ((Enum<?>) constant).name().equals(constantName);
    }
}
