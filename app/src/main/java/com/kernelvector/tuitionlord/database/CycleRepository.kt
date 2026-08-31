package com.kernelvector.tuitionlord.database

class CycleRepository(
    private val database: TuitionDatabase
) : BaseRepository<Cycle, String> {

    override fun getAll(): List<Cycle> =
        database.tuitionQueries.getAllCycles().executeAsList()

    override fun getById(id: String): Cycle? =
        database.tuitionQueries.getCycleById(id).executeAsOneOrNull()

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

    override fun update(item: Cycle) {
        database.tuitionQueries.updateCycle(
            student_id = item.student_id,
            idx = item.idx,
            start_date = item.start_date,
            target_sessions = item.target_sessions,
            status = item.status,
            settled_at = item.settled_at,
            updated_at = item.updated_at,
            id = item.id
        )
    }

    override fun delete(id: String) {
        database.tuitionQueries.deleteCycle(id)
    }

    fun getActiveCycle(studentId: String): Cycle? =
        database.tuitionQueries
            .getActiveCycle(studentId)
            .executeAsOneOrNull()

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
