package com.kernelvector.tuitionlord.database

class StudentRepository(
    private val database: TuitionDatabase
) : BaseRepository <Student> {
    fun getActiveStudents(): List<Student> =
        database.tuitionQueries.getActiveStudents().executeAsList()

    override fun getByID(id: String): Student? =
        database.tuitionQueries.getStudentById(id).executeAsOneOrNull()

    override fun insert(item: Student) {
        database.tuitionQueries.insertStudent(
            id = item.id,
            name = item.name,
            monthly_rate = item.monthly_rate,
            cycle_length_sessions = item.cycle_length_sessions,
            student_phone = item.student_phone,
            guardian_name = item.guardian_name,
            guardian_phone = item.guardian_phone,
            address = item.address,
            subject = item.subject,
            created_at = item.created_at,
            updated_at = item.updated_at,
            is_archived = item.is_archived
        )
    }
}