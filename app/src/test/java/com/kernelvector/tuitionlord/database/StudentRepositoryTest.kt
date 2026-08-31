package com.kernelvector.tuitionlord.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StudentRepositoryTest {

    private lateinit var repository: StudentRepository

    @Before
    fun setUp() {
        repository = StudentRepository(createInMemoryDatabase())
    }

    @Test
    fun insertThenGetByIdReturnsTheRow() {
        val alice = student("1", name = "Alice Rahman")
        repository.insert(alice)

        assertEquals(alice, repository.getById("1"))
    }

    @Test
    fun getByIdReturnsNullForUnknownId() {
        assertNull(repository.getById("nope"))
    }

    @Test
    fun getAllReturnsEveryRowOrderedByName() {
        repository.insert(student("1", name = "Carol"))
        repository.insert(student("2", name = "Alice"))
        repository.insert(student("3", name = "Bob"))

        assertEquals(listOf("Alice", "Bob", "Carol"), repository.getAll().map { it.name })
    }

    @Test
    fun getAllReturnsEmptyListWhenTableIsEmpty() {
        assertTrue(repository.getAll().isEmpty())
    }

    @Test
    fun updateOverwritesMutableColumnsWithoutAddingARow() {
        repository.insert(student("1", name = "Alice", monthlyRate = 300000))
        repository.update(student("1", name = "Alice Rahman", monthlyRate = 350000))

        val stored = repository.getById("1")
        assertEquals(1, repository.getAll().size)
        assertEquals("Alice Rahman", stored?.name)
        assertEquals(350000L, stored?.monthly_rate)
    }

    @Test
    fun updateIsANoOpForUnknownId() {
        repository.update(student("ghost"))

        assertTrue(repository.getAll().isEmpty())
    }

    @Test
    fun deleteRemovesTheRow() {
        repository.insert(student("1"))
        repository.delete("1")

        assertNull(repository.getById("1"))
    }

    @Test
    fun deleteIsANoOpForUnknownId() {
        repository.insert(student("1"))
        repository.delete("nope")

        assertEquals(1, repository.getAll().size)
    }

    @Test
    fun existsReflectsPresence() {
        repository.insert(student("1"))

        assertTrue(repository.exists("1"))
        assertFalse(repository.exists("2"))
    }

    @Test
    fun getActiveStudentsExcludesArchivedOnes() {
        repository.insert(student("1", name = "Alice"))
        repository.insert(student("2", name = "Carol", isArchived = 1))

        assertEquals(listOf("Alice"), repository.getActiveStudents().map { it.name })
    }

    @Test
    fun archiveStudentKeepsTheRowButFlagsIt() {
        repository.insert(student("1"))
        repository.archiveStudent("1", updatedAt = "2026-03-01T09:00:00")

        val stored = repository.getById("1")
        assertEquals(1L, stored?.is_archived)
        assertEquals("2026-03-01T09:00:00", stored?.updated_at)
        assertTrue(repository.getActiveStudents().isEmpty())
    }
}
