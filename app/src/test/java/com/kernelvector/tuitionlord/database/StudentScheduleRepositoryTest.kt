package com.kernelvector.tuitionlord.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StudentScheduleRepositoryTest {

    private lateinit var repository: StudentScheduleRepository

    @Before
    fun setUp() {
        val database = createInMemoryDatabase()
        StudentRepository(database).apply {
            insert(student("s1"))
            insert(student("s2"))
        }
        repository = StudentScheduleRepository(database)
    }

    @Test
    fun insertThenGetByIdReturnsTheRow() {
        val monday = schedule("sc1", "s1")
        repository.insert(monday)

        assertEquals(monday, repository.getById("sc1"))
    }

    @Test
    fun getByIdReturnsNullForUnknownId() {
        assertNull(repository.getById("nope"))
    }

    @Test
    fun getAllReturnsEveryRow() {
        repository.insert(schedule("sc1", "s1", dayOfWeek = "MON"))
        repository.insert(schedule("sc2", "s2", dayOfWeek = "TUE"))

        assertEquals(2, repository.getAll().size)
    }

    @Test
    fun updateOverwritesMutableColumns() {
        repository.insert(schedule("sc1", "s1", startTime = "17:00", duration = 90))
        repository.update(schedule("sc1", "s1", startTime = "18:30", duration = 60))

        val stored = repository.getById("sc1")
        assertEquals(1, repository.getAll().size)
        assertEquals("18:30", stored?.start_time)
        assertEquals(60L, stored?.duration)
    }

    @Test
    fun deleteRemovesOnlyThatSlot() {
        repository.insert(schedule("sc1", "s1", dayOfWeek = "MON"))
        repository.insert(schedule("sc2", "s1", dayOfWeek = "TUE"))
        repository.delete("sc1")

        assertEquals(listOf("sc2"), repository.getSchedulesForStudent("s1").map { it.id })
    }

    @Test
    fun getSchedulesForStudentIsScopedToThatStudent() {
        repository.insert(schedule("sc1", "s1"))
        repository.insert(schedule("sc2", "s2"))

        assertEquals(listOf("sc1"), repository.getSchedulesForStudent("s1").map { it.id })
    }

    @Test
    fun deleteSchedulesForStudentClearsOnlyThatStudentsRoutine() {
        repository.insert(schedule("sc1", "s1", dayOfWeek = "MON"))
        repository.insert(schedule("sc2", "s1", dayOfWeek = "TUE"))
        repository.insert(schedule("sc3", "s2", dayOfWeek = "MON"))

        repository.deleteSchedulesForStudent("s1")

        assertTrue(repository.getSchedulesForStudent("s1").isEmpty())
        assertEquals(listOf("sc3"), repository.getSchedulesForStudent("s2").map { it.id })
    }
}
