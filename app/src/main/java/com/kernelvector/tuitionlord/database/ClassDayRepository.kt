package com.kernelvector.tuitionlord.database

class ClassDayRepository(
    private val database: TuitionDatabase
) : BaseRepository<Class_day, String> {

    override fun getAll(): List<Class_day> =
        database.tuitionQueries.getAllClassDays().executeAsList()

    override fun getById(id: String): Class_day? =
        database.tuitionQueries.getClassDayById(id).executeAsOneOrNull()

    override fun insert(item: Class_day) {
        database.tuitionQueries.insertClassDay(
            id = item.id,
            student_id = item.student_id,
            cycle_id = item.cycle_id,
            date = item.date,
            slot = item.slot,
            status = item.status,
            source = item.source,
            note = item.note,
            created_at = item.created_at,
            updated_at = item.updated_at
        )
    }

    override fun update(item: Class_day) {
        database.tuitionQueries.updateClassDay(
            student_id = item.student_id,
            cycle_id = item.cycle_id,
            date = item.date,
            slot = item.slot,
            status = item.status,
            source = item.source,
            note = item.note,
            updated_at = item.updated_at,
            id = item.id
        )
    }

    override fun delete(id: String) {
        database.tuitionQueries.deleteClassDay(id)
    }

    fun getClassDaysForStudent(studentId: String): List<Class_day> =
        database.tuitionQueries.getClassDaysForStudent(studentId).executeAsList()

    /** The sessions that count toward settlement. */
    fun getHeldClassDaysForCycle(cycleId: String): List<Class_day> =
        database.tuitionQueries
            .getHeldClassDaysForCycle(cycleId)
            .executeAsList()

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
}
