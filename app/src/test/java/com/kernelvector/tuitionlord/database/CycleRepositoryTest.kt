package com.kernelvector.tuitionlord.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CycleRepositoryTest {

    private lateinit var repository: CycleRepository

    @Before
    fun setUp() {
        val database = createInMemoryDatabase()
        StudentRepository(database).insert(student("s1"))
        repository = CycleRepository(database)
    }

    @Test
    fun insertThenGetByIdReturnsTheRow() {
        val first = cycle("c1", "s1")
        repository.insert(first)

        assertEquals(first, repository.getById("c1"))
    }

    @Test
    fun getByIdReturnsNullForUnknownId() {
        assertNull(repository.getById("nope"))
    }

    @Test
    fun getAllReturnsEveryRow() {
        repository.insert(cycle("c1", "s1", idx = 1))
        repository.insert(cycle("c2", "s1", idx = 2))

        assertEquals(2, repository.getAll().size)
    }

    @Test
    fun updateOverwritesMutableColumns() {
        repository.insert(cycle("c1", "s1"))
        repository.update(cycle("c1", "s1", status = "SETTLED", settledAt = "2026-02-01T09:00:00"))

        val stored = repository.getById("c1")
        assertEquals(1, repository.getAll().size)
        assertEquals("SETTLED", stored?.status)
        assertEquals("2026-02-01T09:00:00", stored?.settled_at)
    }

    @Test
    fun deleteRemovesTheRow() {
        repository.insert(cycle("c1", "s1"))
        repository.delete("c1")

        assertNull(repository.getById("c1"))
    }

    @Test
    fun getActiveCycleReturnsOnlyTheOpenOne() {
        repository.insert(cycle("c1", "s1", idx = 1, status = "SETTLED", settledAt = "2026-01-31T09:00:00"))
        repository.insert(cycle("c2", "s1", idx = 2, status = "ACTIVE"))

        assertEquals("c2", repository.getActiveCycle("s1")?.id)
    }

    @Test
    fun getActiveCycleReturnsNullWhenNoneIsOpen() {
        repository.insert(cycle("c1", "s1", status = "SETTLED", settledAt = "2026-01-31T09:00:00"))

        assertNull(repository.getActiveCycle("s1"))
    }

    @Test
    fun settleCycleFlipsStatusAndStamps() {
        repository.insert(cycle("c1", "s1"))
        repository.settleCycle("c1", settledAt = "2026-02-01T09:00:00", updatedAt = "2026-02-01T09:00:00")

        val stored = repository.getById("c1")
        assertEquals("SETTLED", stored?.status)
        assertEquals("2026-02-01T09:00:00", stored?.settled_at)
        assertEquals("2026-02-01T09:00:00", stored?.updated_at)
        assertNull(repository.getActiveCycle("s1"))
    }

    @Test
    fun getActiveCycleIsScopedToTheStudent() {
        repository.insert(cycle("c1", "s1"))

        assertNull(repository.getActiveCycle("someone-else"))
    }
}
