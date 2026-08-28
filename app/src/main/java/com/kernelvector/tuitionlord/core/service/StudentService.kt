package com.kernelvector.tuitionlord.core.service

/**
 * StudentService — Domain-specific service contract for [Student] entities.
 *
 * Extends [BaseService] with student-specific query operations that go beyond
 * the generic CRUD contract.
 *
 * NO ANDROID IMPORTS — pure Kotlin, ready for KMP extraction.
 */
interface StudentService : BaseService<Student, String> {

    /**
     * Find all students whose [Student.name] contains [query] (case-insensitive).
     *
     * @param query The substring to search for within student names.
     * @return A list of matching students; empty list if none match.
     */
    fun findByName(query: String): List<Student>

    /**
     * Retrieve all students filtered by their archived status.
     *
     * @param archived `true` to return only archived students; `false` for active only.
     * @return A list of students matching the [archived] flag.
     */
    fun findByArchivedStatus(archived: Boolean): List<Student>
}
