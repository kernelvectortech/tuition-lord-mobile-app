package com.kernelvector.tuitionlord.database

class SettlementRepository(
    private val database: TuitionDatabase
) : BaseRepository<Settlement, String> {

    override fun getAll(): List<Settlement> =
        database.tuitionQueries.getAllSettlements().executeAsList()

    override fun getById(id: String): Settlement? =
        database.tuitionQueries.getSettlementById(id).executeAsOneOrNull()

    override fun insert(item: Settlement) {
        database.tuitionQueries.insertSettlement(
            id = item.id,
            student_id = item.student_id,
            cycle_id = item.cycle_id,
            sessions_counted = item.sessions_counted,
            amount = item.amount,
            payment_status = item.payment_status,
            collected_at = item.collected_at,
            settled_at = item.settled_at,
            updated_at = item.updated_at,
            note = item.note
        )
    }

    override fun update(item: Settlement) {
        database.tuitionQueries.updateSettlement(
            student_id = item.student_id,
            cycle_id = item.cycle_id,
            sessions_counted = item.sessions_counted,
            amount = item.amount,
            payment_status = item.payment_status,
            collected_at = item.collected_at,
            settled_at = item.settled_at,
            updated_at = item.updated_at,
            note = item.note,
            id = item.id
        )
    }

    override fun delete(id: String) {
        database.tuitionQueries.deleteSettlement(id)
    }

    /** Null until the cycle is settled; settlement.cycle_id is UNIQUE. */
    fun getByCycleId(cycleId: String): Settlement? =
        database.tuitionQueries.getSettlementByCycleId(cycleId).executeAsOneOrNull()

    fun getSettlementsForStudent(studentId: String): List<Settlement> =
        database.tuitionQueries.getSettlementsForStudent(studentId).executeAsList()
}
