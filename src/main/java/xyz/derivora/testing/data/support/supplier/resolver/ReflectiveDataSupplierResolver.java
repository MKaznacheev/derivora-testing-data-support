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

import java.lang.reflect.InvocationTargetException;

/**
 * A {@link DataSupplierResolver} implementation that resolves {@link DataSupplier} instances using reflection.
 *
 * <p>This class instantiates {@link DataSupplier} implementations by dynamically invoking
 * their no-argument constructors via reflection.</p>
 *
 *
 * <p>Instances of this resolver are typically obtained through {@link DataSupplierResolver#getInstance(ArrayGenerator)}.</p>
 *
 * @param <T> the type of elements supplied by the resolved {@link DataSupplier} instances
 */
final class ReflectiveDataSupplierResolver<T> extends AbstractDataSupplierResolver<T> {

    /**
     * Constructs an {@code ReflectiveDataSupplierResolver} with the specified array generator.
     *
     * @param arrayGenerator the generator used to create arrays for resolved suppliers
     * @throws NullPointerException if {@code arrayGenerator} is {@code null}
     */
    ReflectiveDataSupplierResolver(ArrayGenerator<DataSupplier<T>> arrayGenerator) {
        super(arrayGenerator);
    }

    /**
     * Resolves a {@link DataSupplier} instance from the given class reference using reflection.
     *
     * <p>The specified class must have an accessible no-argument constructor. If the class does
     * not meet this requirement, or if instantiation fails for other reasons, an exception is thrown.</p>
     *
     * @param supplierClass the {@link DataSupplier} implementation class to instantiate
     * @return an instance of the specified {@link DataSupplier} class
     * @throws IllegalArgumentException     if the class lacks an accessible no-argument constructor
     * @throws ReflectiveOperationException if instantiation fails due to reflection-related issues
     */
    @Override
    protected DataSupplier<T> resolve(Class<? extends DataSupplier<T>> supplierClass) throws ReflectiveOperationException {
        try {
            return supplierClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("Cannot instantiate supplier: " + supplierClass.getName()
                                                       + " - no accessible no-argument constructor found", e);
        } catch (InstantiationException | IllegalAccessException
                 | IllegalArgumentException | InvocationTargetException e) {
            throw new ReflectiveOperationException("Failed to instantiate supplier: " + supplierClass.getName(), e);
        }
    }
}
