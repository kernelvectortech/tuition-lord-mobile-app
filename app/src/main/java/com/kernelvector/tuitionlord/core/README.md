# Core Package

This package is reserved for domain logic and business rules that are **platform-independent**.

## Architectural Rule

**NO `android.*` imports are permitted in this package.**

This enforces a clear separation between:
- **Core**: Pure Kotlin business logic, data models, use cases (can be extracted to `:shared` KMP module later)
- **UI/Android**: Platform-specific code (Activities, Composables, Intents, etc.)

## Purpose

This boundary exists to facilitate future Kotlin Multiplatform (KMP) migration. When iOS development begins, all code in this package can be moved to a `:shared` KMP module with minimal refactoring.

## Examples of Core Code

- **Calculator.kt** — Simple arithmetic operations (add, subtract, multiply, divide) — _demo module_
- Data classes representing entities (Tuition sessions, guardians, rates)
- Business logic (calculating fees, tracking attendance)
- Use cases / repository interfaces
- Utility functions for date/time calculations

## Examples of Non-Core Code

- Activities and Composables
- Android Context, Intent, SharedPreferences
- UI state management tied to Activity/Fragment lifecycle
- Android-specific concurrency (Handler, Looper)
- Access to Android system services (LocationManager, etc.)

## Migration Path

When `:shared` module is created:
1. Move all code from `core/` to `:shared:src/commonMain/kotlin/`
2. Remove `:app:core/` package
3. Update `:app` to depend on `:shared` (commonMain) and implement platform-specific bridges if needed
