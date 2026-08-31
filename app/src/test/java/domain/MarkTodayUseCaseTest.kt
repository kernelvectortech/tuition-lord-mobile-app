package domain

import com.kernelvector.tuitionlord.database.ClassDayRepository
import com.kernelvector.tuitionlord.database.CycleRepository
import com.kernelvector.tuitionlord.database.StudentRepository
import com.kernelvector.tuitionlord.database.createInMemoryDatabase
import com.kernelvector.tuitionlord.database.cycle
import com.kernelvector.tuitionlord.database.student
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MarkTodayUseCaseTest {

    private lateinit var cycleRepository: CycleRepository
    private lateinit var classDayRepository: ClassDayRepository
    private lateinit var useCase: MarkTodayUseCase

    @Before
    fun setUp() {
        val database = createInMemoryDatabase()
        StudentRepository(database).apply {
            insert(student("s1"))
            insert(student("s2"))
        }
        cycleRepository = CycleRepository(database)
        classDayRepository = ClassDayRepository(database)
        useCase = MarkTodayUseCase(cycleRepository, classDayRepository)
    }

    @Test
    fun marksTodayAsHeldAgainstTheActiveCycle() {
        cycleRepository.insert(cycle("c1", "s1"))

        useCase.MarkToday("s1")

        val marked = classDayRepository.getClassDaysForStudent("s1").single()
        assertEquals("c1", marked.cycle_id)
        assertEquals(today, marked.date)
        assertEquals("HELD", marked.status)
        assertEquals("markToday", marked.source)
        assertEquals(1L, marked.slot)
    }

    @Test
    fun doesNothingWhenTheStudentHasNoCycle() {
        useCase.MarkToday("s1")

        assertTrue(classDayRepository.getAll().isEmpty())
    }

    @Test
    fun doesNothingWhenEveryCycleIsAlreadySettled() {
        cycleRepository.insert(cycle("c1", "s1", status = "SETTLED", settledAt = "2026-01-31T09:00:00"))

        useCase.MarkToday("s1")

        assertTrue(classDayRepository.getAll().isEmpty())
    }

    @Test
    fun marksOnlyTheGivenStudent() {
        cycleRepository.insert(cycle("c1", "s1"))
        cycleRepository.insert(cycle("c2", "s2"))

        useCase.MarkToday("s1")

        assertEquals(1, classDayRepository.getClassDaysForStudent("s1").size)
        assertTrue(classDayRepository.getClassDaysForStudent("s2").isEmpty())
    }

    @Test
    fun theHeldSessionCountsTowardTheCycle() {
        cycleRepository.insert(cycle("c1", "s1"))

        useCase.MarkToday("s1")

        assertEquals(1, classDayRepository.getHeldClassDaysForCycle("c1").size)
    }

    /**
     * Documents current behaviour: UNIQUE(student_id, date, slot) makes a second
     * mark on the same day throw instead of being ignored.
     */
    @Test
    fun markingTheSameDayTwiceViolatesTheUniqueConstraint() {
        cycleRepository.insert(cycle("c1", "s1"))
        useCase.MarkToday("s1")

        assertThrows(Exception::class.java) { useCase.MarkToday("s1") }
        assertEquals(1, classDayRepository.getClassDaysForStudent("s1").size)
    }
}
