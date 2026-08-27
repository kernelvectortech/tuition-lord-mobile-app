package com.kernelvector.tuitionlord.database

import android.content.Context
import java.time.LocalDateTime

object DatabaseExample {
    fun insertAndReadStudent(context: Context): List<Student> {
        val database = TuitionDatabaseFactory.create(context)
        val repository = StudentRepository(database)
        val timestamp = LocalDateTime.now().toString()

        repository.insertStudent(
            Student(
                id = "example-student-1",
                name = "Example Student",
                monthly_rate = 300000,
                cycle_length_sessions = 12,
                student_phone = null,
                guardian_name = "Example Guardian",
                guardian_phone = null,
                address = null,
                subject = "Mathematics",
                created_at = timestamp,
                updated_at = timestamp,
                is_archived = 0
            )
        )

        return repository.getActiveStudents()
    }
}