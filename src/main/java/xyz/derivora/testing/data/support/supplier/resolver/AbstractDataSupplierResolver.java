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

import java.util.Objects;

/**
 * An abstract base class for resolving {@link DataSupplier} instances from their class references.
 *
 * <p>This class provides a framework for resolving multiple {@link DataSupplier} implementations
 * and managing their instantiation using a specified {@link ArrayGenerator}.</p>
 *
 * <p>Subclasses must implement the {@link #resolve(Class)} method to define how individual
 * {@link DataSupplier} instances are created.</p>
 *
 * @param <T> the type of elements supplied by the resolved {@link DataSupplier} instances
 */
public abstract class AbstractDataSupplierResolver<T> implements DataSupplierResolver<T> {

    /**
     * Generates arrays for storing resolved {@link DataSupplier} instances.
     */
    private final ArrayGenerator<DataSupplier<T>> arrayGenerator;

    /**
     * Constructs an {@code AbstractDataSupplierResolver} with the specified array generator.
     *
     * <p>The provided {@link ArrayGenerator} is used to create arrays of resolved
     * {@link DataSupplier} instances.</p>
     *
     * @param arrayGenerator the generator used to create arrays for resolved suppliers
     * @throws NullPointerException if {@code arrayGenerator} is {@code null}
     */
    protected AbstractDataSupplierResolver(ArrayGenerator<DataSupplier<T>> arrayGenerator) {
        this.arrayGenerator = Objects.requireNonNull(arrayGenerator, "Array generator cannot be null");

    }

    /**
     * Resolves an array of {@link DataSupplier} instances from the provided class references.
     *
     * <p>If no classes are provided, an empty array is returned.</p>
     *
     * <p>Neither {@code supplierClasses} nor its elements can be {@code null}.
     * A {@link NullPointerException} will be thrown if the array itself or any of its elements is {@code null}.</p>
     *
     * @param supplierClasses the classes of the {@link DataSupplier} implementations to be instantiated
     * @return an array of resolved {@link DataSupplier} instances
     * @throws NullPointerException if {@code supplierClasses} is {@code null} or contains {@code null} elements
     * @throws Exception if instantiation fails due to an invalid class definition or a reflection error
     */
    @Override
    @SafeVarargs
    public final DataSupplier<T>[] resolve(Class<? extends DataSupplier<T>>... supplierClasses) throws Exception {
        Objects.requireNonNull(supplierClasses, "Supplier classes array cannot be null");

        if (supplierClasses.length == 0) {
            return arrayGenerator.generate(0);
        }

        DataSupplier<T>[] suppliers = arrayGenerator.generate(supplierClasses.length);
        for (int i = 0; i < suppliers.length; i++) {
            Class<? extends DataSupplier<T>> supplierClass = Objects.requireNonNull(supplierClasses[i],
                                                                                    "Supplier class cannot be null");
            suppliers[i] = resolve(supplierClass);
        }

        return suppliers;
    }

    /**
     * Resolves a {@link DataSupplier} instance from the given class reference.
     *
     * @param supplierClass the {@link DataSupplier} implementation class to instantiate
     * @return an instance of the specified {@link DataSupplier} class
     * @throws Exception if instantiation fails due to an invalid class definition or a reflection error
     */
    protected abstract DataSupplier<T> resolve(Class<? extends DataSupplier<T>> supplierClass) throws Exception;
}
