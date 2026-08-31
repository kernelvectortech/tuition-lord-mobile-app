package com.kernelvector.tuitionlord.database

class StudentScheduleRepository(
    private val database: TuitionDatabase
) : BaseRepository<Student_schedule, String> {

    override fun getAll(): List<Student_schedule> =
        database.tuitionQueries.getAllStudentSchedules().executeAsList()

    override fun getById(id: String): Student_schedule? =
        database.tuitionQueries.getStudentScheduleById(id).executeAsOneOrNull()

    override fun insert(item: Student_schedule) {
        database.tuitionQueries.insertStudentSchedule(
            id = item.id,
            student_id = item.student_id,
            day_of_week = item.day_of_week,
            start_time = item.start_time,
            duration = item.duration,
            updated_at = item.updated_at
        )
    }

    override fun update(item: Student_schedule) {
        database.tuitionQueries.updateStudentSchedule(
            student_id = item.student_id,
            day_of_week = item.day_of_week,
            start_time = item.start_time,
            duration = item.duration,
            updated_at = item.updated_at,
            id = item.id
        )
    }

    override fun delete(id: String) {
        database.tuitionQueries.deleteStudentSchedule(id)
    }

    fun getSchedulesForStudent(studentId: String): List<Student_schedule> =
        database.tuitionQueries.getSchedulesForStudent(studentId)
            .executeAsList()

    /** Clears a student's whole weekly routine, for re-saving it in one go. */
    fun deleteSchedulesForStudent(studentId: String) {
        database.tuitionQueries.deleteSchedulesForStudent(studentId)
    }
}
