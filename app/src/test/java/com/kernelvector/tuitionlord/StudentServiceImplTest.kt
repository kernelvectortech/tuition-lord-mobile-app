package com.kernelvector.tuitionlord

import com.kernelvector.tuitionlord.core.service.Student
import com.kernelvector.tuitionlord.core.service.StudentServiceImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the generic service layer.
 *
 * Covers [StudentServiceImpl] which exercises both the inherited
 * [BaseServiceImpl] CRUD operations and the domain-specific methods
 * declared in [StudentService].
 *
 * Run with: ./gradlew test
 */
class StudentServiceImplTest {

    // -------------------------------------------------------------------------
    // Fixtures
    // -------------------------------------------------------------------------

    private lateinit var service: StudentServiceImpl

    private val alice = Student(
        id = "1", name = "Alice Rahman",
        monthlyRate = 300000,           // ৳3000 in poisha
        createdAt = "2025-01-01T09:00:00",
        updatedAt = "2025-01-01T09:00:00",
    )
    private val bob = Student(
        id = "2", name = "Bob Islam",
        monthlyRate = 250000,           // ৳2500 in poisha
        createdAt = "2025-02-01T09:00:00",
        updatedAt = "2025-02-01T09:00:00",
    )
    private val carol = Student(
        id = "3", name = "Carol Hossain",
        monthlyRate = 400000,           // ৳4000 in poisha
        isArchived = true,
        createdAt = "2025-03-01T09:00:00",
        updatedAt = "2025-06-01T09:00:00",
    )

    @Before
    fun setUp() {
        service = StudentServiceImpl()
        // Seed the store before each test
        service.save(alice)
        service.save(bob)
        service.save(carol)
    }

    // -------------------------------------------------------------------------
    // getAll
    // -------------------------------------------------------------------------

    @Test
    fun testGetAllReturnsAllSavedStudents() {
        val result = service.getAll()
        assertEquals(3, result.size)
        assertTrue(result.containsAll(listOf(alice, bob, carol)))
    }

    // -------------------------------------------------------------------------
    // getById
    // -------------------------------------------------------------------------

    @Test
    fun testGetByIdReturnsCorrectStudent() {
        val result = service.getById("2")
        assertEquals(bob, result)
    }

    @Test
    fun testGetByIdReturnsNullForUnknownId() {
        val result = service.getById("999")
        assertNull(result)
    }

    // -------------------------------------------------------------------------
    // save — insert and update
    // -------------------------------------------------------------------------

    @Test
    fun testSaveAddsNewStudent() {
        val dave = Student(
            id = "4", name = "Dave Khan",
            monthlyRate = 350000,
            createdAt = "2025-04-01T09:00:00",
            updatedAt = "2025-04-01T09:00:00",
        )
        service.save(dave)
        assertEquals(4, service.getAll().size)
        assertEquals(dave, service.getById("4"))
    }

    @Test
    fun testSaveUpdatesExistingStudent() {
        val updatedAlice = alice.copy(monthlyRate = 350000)
        service.save(updatedAlice)
        // Size must not grow
        assertEquals(3, service.getAll().size)
        // Stored value must reflect the update
        assertEquals(350000, service.getById("1")?.monthlyRate)
    }

    // -------------------------------------------------------------------------
    // delete
    // -------------------------------------------------------------------------

    @Test
    fun testDeleteReturnsTrueAndRemovesStudent() {
        val deleted = service.delete("1")
        assertTrue(deleted)
        assertEquals(2, service.getAll().size)
        assertNull(service.getById("1"))
    }

    @Test
    fun testDeleteReturnsFalseForUnknownId() {
        val deleted = service.delete("999")
        assertFalse(deleted)
        // Nothing should have been removed
        assertEquals(3, service.getAll().size)
    }

    // -------------------------------------------------------------------------
    // findByName
    // -------------------------------------------------------------------------

    @Test
    fun testFindByNameCaseInsensitive() {
        val result = service.findByName("alice")
        assertEquals(1, result.size)
        assertEquals(alice, result.first())
    }

    @Test
    fun testFindByNameReturnsEmptyListWhenNoMatch() {
        val result = service.findByName("Papaya")
        assertTrue(result.isEmpty())
    }

    @Test
    fun testFindByNamePartialMatch() {
        // "hossain" should match "Carol Hossain"
        val result = service.findByName("hossain")
        assertEquals(1, result.size)
        assertEquals(carol, result.first())
    }

    // -------------------------------------------------------------------------
    // findByArchivedStatus
    // -------------------------------------------------------------------------

    @Test
    fun testFindByArchivedStatusReturnOnlyActiveStudents() {
        val result = service.findByArchivedStatus(archived = false)
        assertEquals(2, result.size)
        assertTrue(result.containsAll(listOf(alice, bob)))
    }

    @Test
    fun testFindByArchivedStatusReturnOnlyArchivedStudents() {
        val result = service.findByArchivedStatus(archived = true)
        assertEquals(1, result.size)
        assertEquals(carol, result.first())
    }

    // -------------------------------------------------------------------------
    // exists
    // -------------------------------------------------------------------------

    @Test
    fun testExistsReflectsPresence() {
        assertTrue(service.exists("1"))
        assertFalse(service.exists("999"))
    }

    @Test
    fun testExistsIsFalseAfterDelete() {
        service.delete("1")
        assertFalse(service.exists("1"))
    }

    // -------------------------------------------------------------------------
    // store isolation
    // -------------------------------------------------------------------------

    @Test
    fun testGetAllReturnsASnapshotNotTheLiveStore() {
        val snapshot = service.getAll()
        service.delete("1")
        // The previously returned list must not shrink with the store
        assertEquals(3, snapshot.size)
        assertEquals(2, service.getAll().size)
    }

    // -------------------------------------------------------------------------
    // findByArchivedStatus
    // -------------------------------------------------------------------------

    @Test
    fun testFindByArchivedStatusReturnsEmptyWhenNoneMatch() {
        // Archive everyone
        service.save(alice.copy(isArchived = true))
        service.save(bob.copy(isArchived = true))
        // Now no active students remain
        val result = service.findByArchivedStatus(archived = false)
        assertTrue(result.isEmpty())
    }
}
