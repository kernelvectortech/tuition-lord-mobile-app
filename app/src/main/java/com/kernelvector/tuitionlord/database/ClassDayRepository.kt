package com.kernelvector.tuitionlord.database

import android.R.id

class ClassDayRepository(
    private val database: TuitionDatabase
) {
    fun insertClassDay(classDay: Class_day) {
        database.tuitionQueries.insertClassDay (
            id = classDay.id,
            student_id = classDay.student_id,
            cycle_id = classDay.cycle_id,
            date = classDay.date,
            slot = classDay.slot,
            status = classDay.status,
            source = classDay.source,
            note = classDay.note,
            created_at = classDay.created_at,
            updated_at = classDay.updated_at
        )
    }

    fun getClassDaysForStudent(studentId: String) : List<Class_day> =
      database.tuitionQueries.getClassDaysForStudent(studentId).executeAsList()

    fun updateClassDayStatus(
        classId: String,
        status: String,
        updatedAt: String
    ) {
       database.tuitionQueries.updateClassDayStatus(
           status = status,
           updated_at = updatedAt,
           id = classId
       )
    }

    fun getHeldClassDaysForCycle(
        cycleId: String
    ): List<Class_day> =
        database.tuitionQueries
            .getHeldClassDaysForCycle(cycleId)
            .executeAsList()

}