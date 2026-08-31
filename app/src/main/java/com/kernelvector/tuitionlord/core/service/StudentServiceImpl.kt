package com.kernelvector.tuitionlord.core.service

/**
 * In-memory [StudentService]. Phase 2 replaces each override with a call into
 * StudentRepository.
 *
 * NO ANDROID IMPORTS - pure Kotlin, ready for KMP extraction.
 */
class StudentServiceImpl : BaseServiceImpl<Student, String>(), StudentService {

    override fun extractId(entity: Student): String = entity.id

    override fun findByName(query: String): List<Student> =
        store.filter { it.name.contains(query, ignoreCase = true) }

    override fun findByArchivedStatus(archived: Boolean): List<Student> =
        store.filter { it.isArchived == archived }
}
