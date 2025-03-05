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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import xyz.derivora.testing.data.support.supplier.resolver.enums.EnumDataSupplierResolver;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("xyz/derivora/testing/data/support/supplier/resolver/enums")
@Tag("base-test")
@DisplayName("Base tests for EnumDataSupplierResolver")
@Disabled("This is a base test class and should not be executed directly")
abstract class EnumDataSupplierResolverTest<T> {

    protected abstract EnumDataSupplierResolver<T> getResolver();

    @Test
    @DisplayName("Should throw NullPointerException when enum type is null")
    void resolve_withNullClass_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> getResolver().resolve(null, "name")
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when constant names array is null")
    void resolve_withNullNamesArray_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> getResolver().resolve(null, (String[]) null)
        );
    }
}
