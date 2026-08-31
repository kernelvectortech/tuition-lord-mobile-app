package domain

import com.kernelvector.tuitionlord.database.ClassDayRepository
import com.kernelvector.tuitionlord.database.Class_day
import com.kernelvector.tuitionlord.database.CycleRepository
import java.util.UUID
import java.time.LocalDate
import java.time.LocalDateTime

val today = LocalDate.now().toString()
val timestamp = LocalDateTime.now().toString()

class MarkTodayUseCase(
    private val cycleRepository: CycleRepository,
    private val classDayRepository: ClassDayRepository
) {
    fun MarkToday(studentId: String) {

        val activeCycle = cycleRepository.getActiveCycle(studentId) ?: return

        classDayRepository.insert(
            Class_day(
                id = UUID.randomUUID().toString(),
                student_id = studentId,
                cycle_id = activeCycle.id,
                date = today,
                status = "HELD",
                source = "markToday",
                slot = 1,
                note = null,
                created_at = timestamp,
                updated_at = timestamp
            )
        )
    }
}