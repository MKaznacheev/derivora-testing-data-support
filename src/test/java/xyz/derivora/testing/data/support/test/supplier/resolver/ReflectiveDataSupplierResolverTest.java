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

package xyz.derivora.testing.data.support.test.supplier.resolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import xyz.derivora.testing.data.support.supplier.DataSupplier;
import xyz.derivora.testing.data.support.supplier.resolver.DataSupplierResolver;
import xyz.derivora.testing.data.support.test.supplier.StringDataSupplier;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/testing/data/support/supplier/resolver")
@DisplayName("Tests for ReflectiveDataSupplierResolver")
class ReflectiveDataSupplierResolverTest extends DataSupplierResolverTest<String> {

    @Override
    protected DataSupplierResolver<String> getResolver() {
        return DataSupplierResolver.getInstance(StringDataSupplier[]::new);
    }

    @Test
    @DisplayName("Should throw NullPointerException when array generator is null")
    void constructor_withNullGenerator_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> DataSupplierResolver.getInstance(null)
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when supplier classes array contains null")
    @SuppressWarnings("unchecked")
    void resolve_withNullClassInArray_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> getResolver().resolve(TestSupplier.class, null)
        );
    }

    @Test
    @DisplayName("Should return an empty array when no supplier classes are provided")
    void resolve_withoutClasses_shouldReturnEmptyArray() throws Exception {
        @SuppressWarnings("unchecked")
        DataSupplier<String>[] suppliers = getResolver().resolve();
        assertEquals(0, suppliers.length);
    }

    @Test
    @DisplayName("Should resolve data supplier instance when class is valid")
    void resolve_withValidClass_shouldResolveDataSupplierInstance() throws Exception {
        @SuppressWarnings("unchecked")
        DataSupplier<String>[] suppliers = getResolver().resolve(TestSupplier.class);

        assertEquals(1, suppliers.length);
        assertInstanceOf(TestSupplier.class, suppliers[0]);
    }

    @Test
    @DisplayName("Should resolve multiple data suppliers when classes are valid")
    void resolve_withValidClasses_shouldResolveMultipleDataSuppliers() throws Exception {
        @SuppressWarnings("unchecked")
        DataSupplier<String>[] suppliers = getResolver().resolve(TestSupplier.class, AnotherTestSupplier.class);

        assertEquals(2, suppliers.length);
        assertInstanceOf(TestSupplier.class, suppliers[0]);
        assertInstanceOf(AnotherTestSupplier.class, suppliers[1]);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when class has not default constructor")
    @SuppressWarnings("unchecked")
    void resolve_whenNoDefaultConstructor_shouldThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> getResolver().resolve(NoDefaultConstructorSupplier.class)
        );
    }

    @Test
    @DisplayName("Should throw ReflectiveOperationException when class is abstract")
    @SuppressWarnings("unchecked")
    void resolve_withAbstractClass_shouldThrowReflectiveOperationException() {
        assertThrows(
                ReflectiveOperationException.class,
                () -> getResolver().resolve(AbstractSupplier.class)
        );
    }

    public static class TestSupplier implements StringDataSupplier {

        @Override
        public String[] get() {
            return new String[]{"Test"};
        }
    }

    public static class AnotherTestSupplier implements StringDataSupplier {

        @Override
        public String[] get() {
            return new String[]{"Another test"};
        }
    }

    public static class NoDefaultConstructorSupplier implements StringDataSupplier {

        private final String value;

        NoDefaultConstructorSupplier(String value) {
            this.value = value;
        }

        @Override
        public String[] get() {
            return new String[]{value};
        }
    }

    public static abstract class AbstractSupplier implements StringDataSupplier {
    }
}
