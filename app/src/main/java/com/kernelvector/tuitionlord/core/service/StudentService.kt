package com.kernelvector.tuitionlord.core.service

/**
 * Service contract for [Student], adding the queries that go beyond generic CRUD.
 *
 * NO ANDROID IMPORTS - pure Kotlin, ready for KMP extraction.
 */
interface StudentService : BaseService<Student, String> {

    /** Case-insensitive substring match on [Student.name]. */
    fun findByName(query: String): List<Student>

    /** `false` gives the active roster, `true` the archived one. */
    fun findByArchivedStatus(archived: Boolean): List<Student>
}
