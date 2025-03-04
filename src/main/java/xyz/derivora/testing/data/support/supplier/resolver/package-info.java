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
 * Provides mechanisms for resolving {@link xyz.derivora.testing.data.support.supplier.DataSupplier} instances.
 *
 * <p>This package contains abstractions and implementations for dynamically instantiating
 * {@link xyz.derivora.testing.data.support.supplier.DataSupplier} implementations.</p>
 *
 * <p>The main interface is {@link xyz.derivora.testing.data.support.supplier.resolver.DataSupplierResolver},
 * which defines the contract for resolving suppliers. The abstract class
 * {@link xyz.derivora.testing.data.support.supplier.resolver.AbstractDataSupplierResolver}
 * provides a base implementation.</p>
 */
package xyz.derivora.testing.data.support.supplier.resolver;