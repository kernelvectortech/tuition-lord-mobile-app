package com.kernelvector.tuitionlord.database

class StudentRepository(
    private val database: TuitionDatabase
) {
    fun getActiveStudents(): List<Student> =
        database.tuitionQueries.getActiveStudents().executeAsList()

    fun getStudentById(id: String): Student? =
        database.tuitionQueries.getStudentById(id).executeAsOneOrNull()

    fun insertStudent(student: Student) {
        database.tuitionQueries.insertStudent(
            id = student.id,
            name = student.name,
            monthly_rate = student.monthly_rate,
            cycle_length_sessions = student.cycle_length_sessions,
            student_phone = student.student_phone,
            guardian_name = student.guardian_name,
            guardian_phone = student.guardian_phone,
            address = student.address,
            subject = student.subject,
            created_at = student.created_at,
            updated_at = student.updated_at,
            is_archived = student.is_archived
        )
    }
}