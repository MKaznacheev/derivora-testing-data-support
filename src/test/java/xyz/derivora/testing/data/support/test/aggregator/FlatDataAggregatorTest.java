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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import xyz.derivora.testing.data.support.aggregator.DataAggregator;
import xyz.derivora.testing.data.support.aggregator.FlatDataAggregator;
import xyz.derivora.testing.data.support.test.supplier.StringDataSupplier;
import xyz.derivora.utilkit.arrays.ArrayUtils;

import static org.junit.jupiter.api.Assertions.*;

@Tag("xyz/derivora/testing/data/support/supplier/aggregator")
@DisplayName("Tests for FlatDataAggregator")
public class FlatDataAggregatorTest extends DataAggregatorTest {

    private static final FlatDataAggregator<String> AGGREGATOR = new FlatDataAggregator<>(String[]::new);

    @Override
    protected DataAggregator<String> getAggregator() {
        return AGGREGATOR;
    }

    @Test
    @DisplayName("Should throw NullPointerException when array generator is null")
    void constructor_withNullGenerator_shouldThrowNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> new FlatDataAggregator<>(null)
        );
    }

    @Test
    @DisplayName("Should return an empty array when no suppliers are provided")
    void aggregate_withoutSuppliers_shouldReturnEmptyArray() {
        String[] data = AGGREGATOR.aggregate();
        assertEquals(0, data.length);
    }

    @Test
    @DisplayName("Should resolve data from single supplier")
    void aggregate_withSingleSupplier_shouldResolveData() {
        TestSupplier supplier = new TestSupplier();
        String[] data = AGGREGATOR.aggregate(supplier);

        assertArrayEquals(supplier.get(), data);
    }

    @Test
    @DisplayName("Should aggregate data from multiple suppliers")
    void aggregate_withMultipleSuppliers_shouldAggregateData() {
        TestSupplier supplier = new TestSupplier();
        AnotherTestSupplier anotherSupplier = new AnotherTestSupplier();

        String[] expectedData = ArrayUtils.merge(supplier.get(), anotherSupplier.get());
        String[] data = AGGREGATOR.aggregate(supplier, anotherSupplier);

        assertArrayEquals(expectedData, data);
    }

    protected static class AnotherTestSupplier implements StringDataSupplier {

        @Override
        public String[] get() {
            return new String[]{"Another test"};
        }
    }
}
