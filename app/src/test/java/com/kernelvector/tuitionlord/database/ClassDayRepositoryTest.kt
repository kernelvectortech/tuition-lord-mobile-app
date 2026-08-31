package com.kernelvector.tuitionlord.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ClassDayRepositoryTest {

    private lateinit var repository: ClassDayRepository

    @Before
    fun setUp() {
        val database = createInMemoryDatabase()
        StudentRepository(database).insert(student("s1"))
        CycleRepository(database).insert(cycle("c1", "s1"))
        repository = ClassDayRepository(database)
    }

    @Test
    fun insertThenGetByIdReturnsTheRow() {
        val day = classDay("d1", "s1", "c1")
        repository.insert(day)

        assertEquals(day, repository.getById("d1"))
    }

    @Test
    fun getByIdReturnsNullForUnknownId() {
        assertNull(repository.getById("nope"))
    }

    @Test
    fun getAllReturnsEveryRowNewestDateFirst() {
        repository.insert(classDay("d1", "s1", "c1", date = "2026-01-05"))
        repository.insert(classDay("d2", "s1", "c1", date = "2026-01-07"))

        assertEquals(listOf("d2", "d1"), repository.getAll().map { it.id })
    }

    @Test
    fun updateOverwritesMutableColumns() {
        repository.insert(classDay("d1", "s1", "c1", status = "SCHEDULED"))
        repository.update(classDay("d1", "s1", "c1", status = "NOT_HELD"))

        assertEquals(1, repository.getAll().size)
        assertEquals("NOT_HELD", repository.getById("d1")?.status)
    }

    @Test
    fun deleteRemovesTheRow() {
        repository.insert(classDay("d1", "s1", "c1"))
        repository.delete("d1")

        assertNull(repository.getById("d1"))
    }

    @Test
    fun getClassDaysForStudentIsScopedToThatStudent() {
        repository.insert(classDay("d1", "s1", "c1"))

        assertEquals(listOf("d1"), repository.getClassDaysForStudent("s1").map { it.id })
        assertTrue(repository.getClassDaysForStudent("someone-else").isEmpty())
    }

    @Test
    fun getHeldClassDaysForCycleReturnsOnlyHeldSessions() {
        repository.insert(classDay("d1", "s1", "c1", date = "2026-01-05", status = "HELD"))
        repository.insert(classDay("d2", "s1", "c1", date = "2026-01-06", status = "SCHEDULED"))
        repository.insert(classDay("d3", "s1", "c1", date = "2026-01-07", status = "NOT_HELD"))

        assertEquals(listOf("d1"), repository.getHeldClassDaysForCycle("c1").map { it.id })
    }

    @Test
    fun updateClassDayStatusTouchesOnlyStatusAndTimestamp() {
        repository.insert(classDay("d1", "s1", "c1", date = "2026-01-05", status = "SCHEDULED"))
        repository.updateClassDayStatus("d1", status = "HELD", updatedAt = "2026-01-05T20:00:00")

        val stored = repository.getById("d1")
        assertEquals("HELD", stored?.status)
        assertEquals("2026-01-05T20:00:00", stored?.updated_at)
        assertEquals("2026-01-05", stored?.date)
    }
}
