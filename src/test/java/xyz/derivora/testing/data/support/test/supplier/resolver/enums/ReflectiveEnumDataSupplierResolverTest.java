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

package xyz.derivora.testing.data.support.test.supplier.resolver.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import xyz.derivora.testing.data.support.supplier.DataSupplier;
import xyz.derivora.testing.data.support.supplier.resolver.enums.EnumDataSupplierResolver;
import xyz.derivora.testing.data.support.test.supplier.StringDataSupplier;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/testing/data/support/supplier/resolver/enums")
@DisplayName("Tests for ReflectiveEnumDataSupplierResolver")
class ReflectiveEnumDataSupplierResolverTest extends EnumDataSupplierResolverTest<String> {

    @Override
    protected EnumDataSupplierResolver<String> getResolver() {
        return EnumDataSupplierResolver.getInstance(StringDataSupplier[]::new);
    }

    @Test
    @DisplayName("Should throw NullPointerException when array generator is null")
    void constructor_withNullGenerator_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> EnumDataSupplierResolver.getInstance(null)
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when constant names array contains null")
    void resolve_withNullNameInArray_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> getResolver().resolve(TestEnum.class, "FIRST", null)
        );
    }

    @Test
    @DisplayName("Should return an empty array when no constant names are provided")
    void resolve_withoutNames_shouldReturnEmptyArray() throws Exception {
        DataSupplier<String>[] suppliers = getResolver().resolve(TestEnum.class);
        assertEquals(0, suppliers.length);
    }

    @Test
    @DisplayName("Should resolve data supplier instance when type and constant name are valid")
    void resolve_withValidTypeAndName_shouldResolveDataSupplierInstance() throws Exception {
        DataSupplier<String>[] suppliers = getResolver().resolve(TestEnum.class, TestEnum.FIRST.name());

        assertEquals(1, suppliers.length);
        assertEquals(TestEnum.FIRST, suppliers[0]);
    }

    @Test
    @DisplayName("Should resolve multiple data suppliers when type and constant names are valid")
    void resolve_withValidTypeAndNames_shouldResolveMultipleDataSuppliers() throws Exception {
        DataSupplier<String>[] suppliers = getResolver().resolve(TestEnum.class,
                                                                 TestEnum.FIRST.name(), TestEnum.SECOND.name());

        assertEquals(2, suppliers.length);
        assertEquals(TestEnum.FIRST, suppliers[0]);
        assertEquals(TestEnum.SECOND, suppliers[1]);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when class is not enum")
    void resolve_withNotEnum_shouldThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> getResolver().resolve(NotEnum.class)
        );
    }

    public enum TestEnum implements StringDataSupplier {

        FIRST {
            @Override
            public String[] get() {
                return new String[]{"First"};
            }
        },

        SECOND {
            @Override
            public String[] get() {
                return new String[]{"Second"};
            }
        }
    }

    public static class NotEnum implements StringDataSupplier {

        @Override
        public String[] get() {
            return new String[]{"Wrong type"};
        }
    }
}
