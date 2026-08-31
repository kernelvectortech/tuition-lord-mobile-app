package com.kernelvector.tuitionlord.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SettlementRepositoryTest {

    private lateinit var repository: SettlementRepository

    @Before
    fun setUp() {
        val database = createInMemoryDatabase()
        StudentRepository(database).insert(student("s1"))
        CycleRepository(database).apply {
            insert(cycle("c1", "s1", idx = 1))
            insert(cycle("c2", "s1", idx = 2))
        }
        repository = SettlementRepository(database)
    }

    @Test
    fun insertThenGetByIdReturnsTheRow() {
        val paid = settlement("st1", "s1", "c1")
        repository.insert(paid)

        assertEquals(paid, repository.getById("st1"))
    }

    @Test
    fun getByIdLooksUpThePrimaryKeyNotTheCycleId() {
        repository.insert(settlement("st1", "s1", "c1"))

        assertEquals("st1", repository.getById("st1")?.id)
        assertNull(repository.getById("c1"))
    }

    @Test
    fun getByCycleIdFindsTheSettlementOfThatCycle() {
        repository.insert(settlement("st1", "s1", "c1"))

        assertEquals("st1", repository.getByCycleId("c1")?.id)
    }

    @Test
    fun getByCycleIdReturnsNullWhileTheCycleIsUnsettled() {
        assertNull(repository.getByCycleId("c2"))
    }

    @Test
    fun getAllReturnsEveryRow() {
        repository.insert(settlement("st1", "s1", "c1"))
        repository.insert(settlement("st2", "s1", "c2"))

        assertEquals(2, repository.getAll().size)
    }

    @Test
    fun updateOverwritesMutableColumns() {
        repository.insert(settlement("st1", "s1", "c1", paymentStatus = "DUE"))
        repository.update(settlement("st1", "s1", "c1", amount = 250000, paymentStatus = "COLLECTED"))

        val stored = repository.getById("st1")
        assertEquals(1, repository.getAll().size)
        assertEquals("COLLECTED", stored?.payment_status)
        assertEquals(250000L, stored?.amount)
    }

    @Test
    fun deleteRemovesTheRow() {
        repository.insert(settlement("st1", "s1", "c1"))
        repository.delete("st1")

        assertNull(repository.getById("st1"))
        assertNull(repository.getByCycleId("c1"))
    }

    @Test
    fun getSettlementsForStudentIsScopedToThatStudent() {
        repository.insert(settlement("st1", "s1", "c1"))

        assertEquals(listOf("st1"), repository.getSettlementsForStudent("s1").map { it.id })
        assertTrue(repository.getSettlementsForStudent("someone-else").isEmpty())
    }
}
