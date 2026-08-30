package com.kernelvector.tuitionlord.database

class SettlementRepository(
    private val database: TuitionDatabase
): BaseRepository <Settlement> {

    override fun getByID(id: String) : Settlement? =
        database.tuitionQueries.getSettlementByCycleId(id).executeAsOneOrNull()


    fun getSettlementsForStudent(studentId: String) :List<Settlement> =
        database.tuitionQueries.getSettlementsForStudent(studentId).executeAsList()

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
}