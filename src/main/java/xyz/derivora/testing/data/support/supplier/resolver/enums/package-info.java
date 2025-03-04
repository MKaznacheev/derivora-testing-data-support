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

/**
 * Provides implementations for resolving {@link xyz.derivora.testing.data.support.supplier.DataSupplier} instances from enumerations.
 *
 * <p>This package contains resolver implementations that extract {@link xyz.derivora.testing.data.support.supplier.DataSupplier} instances
 * from enumeration constants, assuming that the enum itself implements {@link xyz.derivora.testing.data.support.supplier.DataSupplier}.</p>
 *
 * <p>The main interface is {@link xyz.derivora.testing.data.support.supplier.resolver.enums.EnumDataSupplierResolver},
 * which defines the contract for resolving suppliers from enumeration types.</p>
 *
 * <p>The abstract class {@link xyz.derivora.testing.data.support.supplier.resolver.enums.AbstractEnumDataSupplierResolver}
 * provides a base implementation.</p>
 */
package xyz.derivora.testing.data.support.supplier.resolver.enums;