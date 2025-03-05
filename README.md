# Derivora Testing Data Support

## About the Project

Derivora Testing Data Support is a module designed to enhance data-driven and parameterized testing in JUnit 5.
This module is part of the derivora project and provides utilities and annotations to simplify test data management
and execution.

At this stage, the project is in its initialization phase.

## Objectives of the Module

* **Enhance test automation:** Provide structured support for parameterized testing.
* **Improve maintainability:** Offer reusable components for managing test data.
* **Seamless integration:** Ensure compatibility with JUnit 5 and existing testing workflows.

## Project Structure

* Package `xyz.derivora.testing.data.support.supplier` provides interface for supplying structured data in array form.
  * Subpackage `xyz.derivora.testing.data.support.aggregator` provides mechanisms for aggregating data from multiple `DataSupplier` instances.
  * Subpackage `xyz.derivora.testing.data.support.provider` provides base classes for supplying test data to JUnit parameterized tests.
  * Subpackage `xyz.derivora.testing.data.support.supplier.resolver` provides mechanisms for resolving `DataSupplier` instances.
    * Subpackage `xyz.derivora.testing.data.support.supplier.resolver.enums` provides implementations for resolving `DataSupplier` instances from enumerations.

## License

This project is licensed under the GNU Lesser General Public License v3.0.
See the [LICENSE](./LICENSE) file for details.