package com.kernelvector.tuitionlord.core.service

/**
 * Student — Lightweight domain entity used by [StudentService].
 *
 * Fields mirror the `student` table defined in the database schema.
 * Money values ([monthlyRate]) are stored in **minor units (poisha)**
 * to avoid floating-point rounding errors; convert to taka only at the
 * display layer (divide by 100).
 *
 * This is a pure-Kotlin data class with no Android dependencies, making it
 * safe for future extraction into a KMP `:shared` module.
 *
 * @property id                  Unique identifier (UUID string).
 * @property name                Human-readable student name.
 * @property monthlyRate         Monthly tuition fee in minor units (poisha).
 * @property cycleLengthSessions Number of sessions that make up one cycle. Default 12.
 * @property studentPhone        Student's own contact number. Nullable.
 * @property guardianName        Guardian's full name. Nullable.
 * @property guardianPhone       Guardian's contact number. Nullable.
 * @property address             Home address. Nullable.
 * @property subject             Subject(s) being taught. Nullable.
 * @property createdAt           ISO-8601 datetime when the record was first created.
 * @property updatedAt           ISO-8601 datetime when the record was last modified.
 * @property isArchived          Soft-delete flag. `true` = archived; `false` = active.
 */
data class Student(
    val id: String,
    val name: String,
    val monthlyRate: Int,
    val cycleLengthSessions: Int = 12,
    val studentPhone: String? = null,
    val guardianName: String? = null,
    val guardianPhone: String? = null,
    val address: String? = null,
    val subject: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val isArchived: Boolean = false,
)
