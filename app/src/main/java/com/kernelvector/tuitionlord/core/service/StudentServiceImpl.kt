package com.kernelvector.tuitionlord.core.service

/**
 * StudentServiceImpl — Concrete service for [Student] entities.
 *
 * Extends [BaseServiceImpl] (inheriting the default in-memory CRUD operations)
 * and implements [StudentService] (honouring the student-specific contract).
 *
 * ### Swapping in SQLDelight (Phase 2)
 * Override [getAll], [getById], [save], and [delete] to delegate to the
 * generated SQLDelight `StudentQueries`. The domain methods [findByName] and
 * [findByArchivedStatus] can similarly be replaced with typed SQL queries.
 *
 * NO ANDROID IMPORTS — pure Kotlin, ready for KMP extraction.
 */
class StudentServiceImpl : BaseServiceImpl<Student, String>(), StudentService {

    // -------------------------------------------------------------------------
    // BaseServiceImpl — abstract implementation
    // -------------------------------------------------------------------------

    /**
     * Returns [Student.id] as the stable identifier for in-store lookup.
     *
     * @param entity The student whose ID to extract.
     * @return The student's unique string identifier.
     */
    override fun extractId(entity: Student): String = entity.id

    // -------------------------------------------------------------------------
    // StudentService — domain-specific operations
    // -------------------------------------------------------------------------

    /**
     * Returns all students whose [Student.name] contains [query],
     * ignoring letter case.
     *
     * @param query Substring to match within student names.
     * @return Filtered list of matching students; empty if none match.
     */
    override fun findByName(query: String): List<Student> =
        store.filter { it.name.contains(query, ignoreCase = true) }

    /**
     * Returns all students whose [Student.isArchived] flag equals [archived].
     *
     * Use `archived = false` to list active students, and `archived = true`
     * to view the archived roster.
     *
     * @param archived The target archived state to filter by.
     * @return Filtered list of students with the matching archived status.
     */
    override fun findByArchivedStatus(archived: Boolean): List<Student> =
        store.filter { it.isArchived == archived }
}
