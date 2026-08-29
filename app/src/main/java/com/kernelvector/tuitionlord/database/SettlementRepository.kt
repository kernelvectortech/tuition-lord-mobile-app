package com.kernelvector.tuitionlord.database

class SettlementRepository(
    private val database: TuitionDatabase
) {
    fun getSettlementByCycleId(cycleId: String) : Settlement? =
        database.tuitionQueries.getSettlementByCycleId(cycleId).executeAsOneOrNull()


    fun getSettlementsForStudent(studentId: String) :List<Settlement> =
        database.tuitionQueries.getSettlementsForStudent(studentId).executeAsList()

    fun insertSettlement(settlement: Settlement) {
        database.tuitionQueries.insertSettlement(
            id = settlement.id,
            student_id = settlement.student_id,
            cycle_id = settlement.cycle_id,
            sessions_counted = settlement.sessions_counted,
            amount = settlement.amount,
            payment_status = settlement.payment_status,
            collected_at = settlement.collected_at,
            settled_at = settlement.settled_at,
            updated_at = settlement.updated_at,
            note = settlement.note
        )
    }
}