package com.kernelvector.tuitionlord.database

import android.R.id



class StudentScheduleRepository(
    private val database: TuitionDatabase
) {
    fun getSchedulesForStudent(studentId: String) : List<Student_schedule> =
        database.tuitionQueries.getSchedulesForStudent(studentId)
            .executeAsList()

    fun deleteStudentSchedule(studentId: String) = database.tuitionQueries
        .deleteStudentSchedule(studentId)

    fun insertStudentSchedule(studentSchedule: Student_schedule) {
        database.tuitionQueries.insertStudentSchedule(
            id = studentSchedule.id,
            student_id = studentSchedule.student_id,
            day_of_week = studentSchedule.day_of_week,
            start_time = studentSchedule.start_time,
            duration = studentSchedule.duration,
            updated_at = studentSchedule.updated_at
        )
    }
}