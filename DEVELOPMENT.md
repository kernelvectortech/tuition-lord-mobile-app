# Development Guide — Getting Started

## Quick Start: Add a New Module

### 1. Create Business Logic in `core/`

Example: `Calculator.kt` (pure Kotlin, no Android imports) — **Already implemented in this project**

```kotlin
package com.kernelvector.tuitionlord.core

object Calculator {
    fun add(a: Int, b: Int): Int = a + b
    fun subtract(a: Int, b: Int): Int = a - b
    fun multiply(a: Int, b: Int): Int = a * b
    fun divide(a: Int, b: Int): Int {
        if (b == 0) throw IllegalArgumentException("Cannot divide by zero")
        return a / b
    }
}
```

✅ **Location:** `app/src/main/java/com/kernelvector/tuitionlord/core/Calculator.kt`

**Key rules:**
- ✅ Pure Kotlin only
- ✅ No `android.*` imports
- ✅ Reusable, testable functions
- ✅ Clear documentation with KDoc
- ❌ No Activity, Context, Intent, etc.

### 2. Write Unit Tests

Create corresponding test in `app/src/test/java/`

```kotlin
package com.kernelvector.tuitionlord

import org.junit.Test
import org.junit.Assert.*
import com.kernelvector.tuitionlord.core.Calculator

class CalculatorTest {
    @Test
    fun testAdd() {
        assertEquals(30, Calculator.add(10, 20))
    }
}
```

**Run tests:**
```bash
./gradlew test
```

### 3. Use in UI (Optional)

If needed in Compose screens, import and call from `ui/screens/` and toggle in `MainActivity.kt`

**Example: CalculatorScreen.kt** (fully implemented demo)
```kotlin
import com.kernelvector.tuitionlord.core.Calculator

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var numberOne by remember { mutableStateOf("") }
    var numberTwo by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    
    Button(onClick = {
        val a = numberOne.toInt()
        val b = numberTwo.toInt()
        result = "Result: ${Calculator.add(a, b)}"
    }) {
        Text("Add")
    }
    
    // ... other operations and UI
}
```

**Toggle in MainActivity.kt:**
```kotlin
// TOGGLE: Uncomment below to show Calculator, comment to show Greeting
CalculatorScreen(modifier = Modifier.padding(innerPadding))
// Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
```

To **disable calculator**: Comment line with `CalculatorScreen(...)` and uncomment `Greeting(...)` line.

---



---

## File Structure for New Module

```
app/src/main/java/com/kernelvector/tuitionlord/
├── core/
│   ├── Calculator.kt              ← Business logic (pure Kotlin)
│   ├── TuitionSession.kt          ← Data model
│   ├── SessionFeeCalculator.kt    ← Service/use case
│   └── Guardian.kt                ← Entity
│
└── ui/
    ├── MainActivity.kt
    ├── screens/
    │   ├── SessionListScreen.kt
    │   └── AddSessionScreen.kt
    └── theme/

app/src/test/java/com/kernelvector/tuitionlord/
├── CalculatorTest.kt              ← Unit tests
├── SessionFeeCalculatorTest.kt    ← Unit tests
└── TuitionSessionTest.kt          ← Unit tests
```

---

## Naming Conventions

| Type | Example | Notes |
|------|---------|-------|
| Data class | `TuitionSession` | PascalCase |
| Service/object | `SessionFeeCalculator` | PascalCase |
| Function | `calculateFee()` | camelCase |
| Constant | `MAX_SESSION_HOURS` | UPPER_SNAKE_CASE |
| Test class | `CalculatorTest` | Add "Test" suffix |
| Test method | `testAddIntegers()` | Start with "test" |

---

## Gradle Commands

```bash
# Sync dependencies
./gradlew sync

# Run unit tests
./gradlew test

# Build debug APK
./gradlew assembleDebug

# Run app on emulator/device
./gradlew installDebug

# Clean build
./gradlew clean

# Run with info
./gradlew test --info
```





---

## Next Phase: Database (Phase 2)

When ready to add database layer:
1. Add Room dependency to `build.gradle.kts`
2. Create `@Entity` classes in `core/` (extend data models)
3. Create `@Dao` interfaces in `core/` (data access)
4. Create `Repository` class in `core/` (high-level operations)
5. Write tests for database queries

Example structure:
```
core/
├── entity/
│   ├── SessionEntity.kt           ← Room @Entity
│   └── GuardianEntity.kt
├── dao/
│   ├── SessionDao.kt              ← Room @Dao
│   └── GuardianDao.kt
└── repository/
    ├── SessionRepository.kt       ← Business layer
    └── GuardianRepository.kt
```
