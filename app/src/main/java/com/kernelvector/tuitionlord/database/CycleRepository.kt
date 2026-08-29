package com.kernelvector.tuitionlord.database

class CycleRepository(
    private val database: TuitionDatabase
) {
    fun getActiveCycle(studentId: String): Cycle? =
        database.tuitionQueries
            .getActiveCycle(studentId)
            .executeAsOneOrNull()

    fun getCycleById(cycleId: String): Cycle? =
        database.tuitionQueries
            .getCycleById(cycleId)
            .executeAsOneOrNull()

    fun insertCycle(cycle: Cycle) {
        database.tuitionQueries.insertCycle(
            id = cycle.id,
            student_id = cycle.student_id,
            idx = cycle.idx,
            start_date = cycle.start_date,
            target_sessions = cycle.target_sessions,
            status = cycle.status,
            settled_at = cycle.settled_at,
            updated_at = cycle.updated_at
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

