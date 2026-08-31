package com.kernelvector.tuitionlord.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented counterpart to the JVM repository tests: the JVM suite uses an
 * in-memory JDBC driver, so this is the only place [TuitionDatabaseFactory] and
 * AndroidSqliteDriver are actually exercised.
 *
 * Run with: ./gradlew connectedDebugAndroidTest (needs a device or emulator).
 */
@RunWith(AndroidJUnit4::class)
class TuitionDatabaseFactoryTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var database: TuitionDatabase

    @Before
    fun setUp() {
        context.deleteDatabase("tuition.db")
        database = TuitionDatabaseFactory.create(context)
    }

    @After
    fun tearDown() {
        context.deleteDatabase("tuition.db")
    }

    @Test
    fun createBuildsAUsableSchema() {
        val repository = StudentRepository(database)
        repository.insert(
            Student(
                id = "s1",
                name = "Alice Rahman",
                monthly_rate = 300000,
                cycle_length_sessions = 12,
                student_phone = null,
                guardian_name = null,
                guardian_phone = null,
                address = null,
                subject = null,
                created_at = "2026-01-01T09:00:00",
                updated_at = "2026-01-01T09:00:00",
                is_archived = 0
            )
        )

        assertNotNull(repository.getById("s1"))
        assertEquals(1, repository.getAll().size)
    }

    @Test
    fun foreignKeysAreEnforced() {
        val cycleRepository = CycleRepository(database)

        // cycle.student_id references a student that was never inserted
        assertThrows(Exception::class.java) {
            cycleRepository.insert(
                Cycle(
                    id = "c1",
                    student_id = "ghost",
                    idx = 1,
                    start_date = "2026-01-01",
                    target_sessions = 12,
                    status = "ACTIVE",
                    settled_at = null,
                    updated_at = "2026-01-01T09:00:00"
                )
            )
        }
    }
}
