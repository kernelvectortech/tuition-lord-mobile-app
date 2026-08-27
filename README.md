# Tuition Lord — Mobile App

Local-first, offline tuition tracker for home tutors. No backend, no login, all data stored on-device.

**Current Phase:** Environment setup ✓ | SQLDelight database integrated ✓ | Demo calculator included ✓

---

## Project Structure

```
app/
├── src/main/java/com/kernelvector/tuitionlord/
│   ├── core/                      CRITICAL — Platform-independent business logic (NO android.* imports)
│   │   ├── Calculator.kt          (Demo module: arithmetic operations)
│   │   └── README.md              (Explains KMP boundary rule)
│   ├── MainActivity.kt            CRITICAL — Main entry point; update for app initialization
│   ├── TuitionLordApplication.kt   — Creates the SQLDelight database and repository
│   ├── database/                   — SQLDelight factory, schema access, and repository example
│   ├── ui/                        CRITICAL — Composables, screens; most frequent changes
│   │   ├── screens/
│   │   │   └── CalculatorScreen.kt (Demo UI: shows how to use Calculator core module)
│   │   └── theme/                (Set once; rarely changes)
│   └── AndroidManifest.xml        (Manifest configuration; update for new features/permissions)
├── build.gradle.kts               CRITICAL — Dependencies & build config; update when adding libraries
├── gradle/libs.versions.toml      (Dependency version catalog; update with build.gradle.kts)
└── res/                           (Strings, colors, icons; occasional updates)
```

**Legend:**  
**CRITICAL** = Frequently modified during active development  
Regular = Updated as needed for new features

---

## Clone & Setup

### Prerequisites (see [Environment Setup](ANDROID_STUDIO_SETUP.md))
- **Android Studio** (latest stable) — [download](https://developer.android.com/studio)
- **JDK 11+** (bundled with Android Studio)
- **Gradle Wrapper** (included in repo; no separate install needed)

### Setup Steps

1. **Clone the repository:**
   ```bash
   git clone <repo-url>
   cd tuition-lord-mobile-app
   ```

2. **Open in Android Studio:**
   - `File → Open → [path to project]`
   - Gradle will auto-sync; wait for completion

3. **Create an Android Emulator (or use connected device):**
   - `Tools → Device Manager → Create Device`
   - Select a device (e.g., Medium Phone)
   - Choose System Image (Android 14 or later)
   - Start the emulator

---

## Build Debug APK

### Command Line
```bash
# Sync dependencies
./gradlew sync

# Build debug APK
./gradlew assembleDebug

# Install & run on emulator/device
./gradlew installDebug
```

### Android Studio
1. `Run → Run 'app'` (or `Shift+F10`)
2. Select running emulator/device
3. Click OK

### Run Tests
```bash
./gradlew test                    # Unit tests (fast)
./gradlew connectedAndroidTest    # Instrumented tests (needs emulator/device)
```

---

## Configuration

| Setting | Value |
|---------|-------|
| **compileSdk/targetSdk** | 36 (Android 9/15) |
| **minSdk** | 26 (Android 8.0) |
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Build System** | Gradle + Kotlin DSL |

---

## Key Design Decisions

- **Single-module:** No KMP `:shared` module yet; deferred to Phase 5 (iOS)
- **Compose only:** No XML layouts; purely declarative UI
- **Local data:** SQLite + SQLDelight (no cloud sync, no backend) — see [ADR 0001](docs/adr/0001-persistence-library-sqldelight.md)
- **Zero dangerous permissions:** Only implicit `INTERNET` + `AD_ID` from AdMob SDK
- **Core package boundary:** `core/` reserves business logic for future KMP extraction

---

## Database Example

The app creates the local SQLDelight database on startup. The schema and queries are in:

```text
app/src/main/sqldelight/com/kernelvector/tuitionlord/database/Tuition.sq
```

The generated database is created by `TuitionDatabaseFactory`. To insert and read a sample student from Kotlin:

```kotlin
val students = DatabaseExample.insertAndReadStudent(context)
```

For normal app code, use the repository exposed by `TuitionLordApplication`:

```kotlin
val app = applicationContext as TuitionLordApplication
val students = app.studentRepository.getActiveStudents()
```

The SQLite file is stored privately on the device as `tuition.db`. Money is stored as integer poisha, dates and timestamps as ISO-8601 text, and SQLite foreign keys are enabled when the driver opens.

---

## Getting Started with Development

**→ See [DEVELOPMENT.md](DEVELOPMENT.md) for:**
- How to add new modules (Calculator boilerplate included)
- Writing unit tests (TDD workflow)
- Git workflow & branching strategy
- File structure & naming conventions
- Example: TuitionSession domain model

**Quick test:**
```bash
./gradlew test  # Runs Calculator + Example tests
```

---

**Phase 2:** SQLDelight database, data models, repository layer  
**Phase 3:** UI screens (dashboard, session tracking, guardian management)  
**Phase 4:** Local notifications, CSV/PDF export  
**Phase 5:** Kotlin Multiplatform + iOS

---

## Resources

- [Android Developers](https://developer.android.com/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Google Play Console](https://support.google.com/googleplay/android-developer)

---
