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

package xyz.derivora.testing.data.support.test.aggregator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import xyz.derivora.testing.data.support.aggregator.DataAggregator;
import xyz.derivora.testing.data.support.test.supplier.StringDataSupplier;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("xyz/derivora/testing/data/support/supplier/aggregator")
@Tag("base-test")
@DisplayName("Base tests for DataAggregator")
@Disabled("This is a base test class and should not be executed directly")
abstract class DataAggregatorTest {

    protected abstract DataAggregator<String> getAggregator();

    @Test
    @DisplayName("Should throw NullPointerException when suppliers array is null")
    void aggregate_withNullArray_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> getAggregator().aggregate((TestSupplier[]) null)
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when suppliers array contains null")
    void aggregate_withNullSupplierInArray_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> getAggregator().aggregate(new TestSupplier(), null)
        );
    }

    protected static class TestSupplier implements StringDataSupplier {

        @Override
        public String[] get() {
            return new String[]{"Test"};
        }
    }
}
