package com.kernelvector.tuitionlord.database

class CycleRepository(
    private val database: TuitionDatabase
) : BaseRepository <Cycle> {
    fun getActiveCycle(studentId: String): Cycle? =
        database.tuitionQueries
            .getActiveCycle(studentId)
            .executeAsOneOrNull()

    override fun getByID(id: String): Cycle? =
        database.tuitionQueries
            .getCycleById(id)
            .executeAsOneOrNull()

    override fun insert(item: Cycle) {
        database.tuitionQueries.insertCycle(
            id = item.id,
            student_id = item.student_id,
            idx = item.idx,
            start_date = item.start_date,
            target_sessions = item.target_sessions,
            status = item.status,
            settled_at = item.settled_at,
            updated_at = item.updated_at
        )
    }

    fun settleCycle(
        cycleId: String,
        settledAt: String,
        updatedAt: String
    ) {
        database.tuitionQueries.settleCycle(
            settled_at = settledAt,
            updated_at = updatedAt,
            id = cycleId
        )
    }
}

