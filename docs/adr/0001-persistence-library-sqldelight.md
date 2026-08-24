# ADR 0001: Choose persistence library (SQLDelight)

**Status:** Accepted  
**Date:** 2026-08-23  
**Issue:** [TL-001 / #6](https://github.com/kernelvectortech/tuition-lord-mobile-app/issues/6)  
**Deciders:** Core + data owners

## Context

Tuition Lord is local-first and offline-only. All tuition data lives in on-device SQLite. The canonical schema is already defined as SQL DDL in [`tuition-lord-docs/database/schema.md`](https://github.com/kernelvectortech/tuition-lord-docs/blob/main/database/schema.md) (five tables: `student`, `cycle`, `class_day`, `student_schedule`, `settlement`).

The `core/` package must stay free of `android.*` imports so business logic and data access can move into a KMP `:shared` module later (Phase 5 / iOS).

## Decision

Use **SQLDelight** for local persistence.

## Rationale

| Factor | Why SQLDelight |
|--------|----------------|
| Schema format | Docs are already SQL DDL; SQLDelight consumes `.sq` files directly |
| Type safety | Generated Kotlin APIs for queries — no stringly-typed SQL at call sites |
| KMP readiness | Pure Kotlin; same schema and queries on Android and iOS |
| `core/` boundary | Fits the platform-independent package without Room's Android coupling |
| Relational model | FKs, CHECK constraints, and derived reads map cleanly to explicit SQL |

## Alternatives considered

### Room (rejected for now)

- Android-first; Room KMP exists but adds weight and platform branching earlier than needed.
- Annotation-driven entities duplicate work when the schema is already specified as SQL.
- Would require rework when extracting `core/` to KMP.

## Consequences

**Positive**

- One schema definition shared across platforms long-term.
- Queries stay explicit and reviewable (important for settlement math and audit trails).
- Aligns with system design in `tuition-lord-docs`.

**Negative / trade-offs**

- Team must learn SQLDelight `.sq` files and Gradle plugin setup.
- Migrations must be authored deliberately from day one (required by product either way).

## Example (Tuition Lord)

Instead of a Room `@Entity` + `@Dao` for counting held sessions in a cycle:

```kotlin
// Room-style (not chosen)
@Query("SELECT COUNT(*) FROM class_day WHERE cycle_id = :cycleId AND status = 'HELD'")
suspend fun countHeldSessions(cycleId: String): Int
```

SQLDelight keeps the query in SQL and generates a typed API:

```sql
-- ClassDay.sq
countHeldByCycle:
SELECT COUNT(*)
FROM class_day
WHERE cycle_id = ? AND status = 'HELD';
```

```kotlin
// Generated usage in core/
val sessionsHeld = queries.countHeldByCycle(cycleId).executeAsOne()
```

The schema in `schema.md` becomes the source of truth; Kotlin types are generated, not hand-maintained.

## References

- [tuition-lord-docs — database/schema.md](https://github.com/kernelvectortech/tuition-lord-docs/blob/main/database/schema.md)
- [tuition-lord-docs — system-design/system-design.md](https://github.com/kernelvectortech/tuition-lord-docs/blob/main/system-design/system-design.md)
- [SQLDelight documentation](https://sqldelight.github.io/sqldelight/latest/)
