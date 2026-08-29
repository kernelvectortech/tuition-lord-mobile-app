package com.kernelvector.tuitionlord.database

import android.content.Context
import java.time.LocalDateTime
import java.time.LocalDate
import android.util.Log

val timestamp = LocalDateTime.now().toString()

object DatabaseExample {

    fun insertAndReadStudent(context: Context): List<Student> {
        val database = TuitionDatabaseFactory.create(context)
        val repository = StudentRepository(database)
        val timestamp = LocalDateTime.now().toString()

        repository.insertStudent(
            Student(
                id = "example-student-1",
                name = "Example Student",
                monthly_rate = 300000,
                cycle_length_sessions = 12,
                student_phone = null,
                guardian_name = "Example Guardian",
                guardian_phone = null,
                address = null,
                subject = "Mathematics",
                created_at = timestamp,
                updated_at = timestamp,
                is_archived = 0
            )
        )

        return repository.getActiveStudents()
    }

    fun insertAndRead(context: Context) {
        val database = TuitionDatabaseFactory.create(context)
        val repository = CycleRepository(database)

        repository.insertCycle(
            Cycle(
            id = "cycle-1",
            student_id = "example-student-1",
            idx = 1,
            start_date = "2026-08-01",
            target_sessions = 12,
            status = "ACTIVE",
            settled_at = null,
            updated_at = "2026-08-01T10:00:00"
            )
        )
        val cycle = repository.getActiveCycle("example-student-1")
        println(cycle)
    }

    fun testMarkToday(context: Context) {
        val database = TuitionDatabaseFactory.create(context)
        val studentRepository = StudentRepository(database)
        val cycleRepository = CycleRepository(database)
        val classDayRepository = ClassDayRepository(database)
        val timestamp = LocalDateTime.now().toString()

        val student = Student(
            id = "example-student-2",
            name = "Example Student",
            monthly_rate = 300000,
            cycle_length_sessions = 12,
            student_phone = null,
            guardian_name = "Example Guardian",
            guardian_phone = null,
            address = null,
            subject = "Mathematics",
            created_at = timestamp,
            updated_at = timestamp,
            is_archived = 0
        )
        studentRepository.insertStudent(student)

        val cycle = Cycle(
            id = "cycle-1",
            student_id = "example-student-2",
            idx = 1,
            start_date = "2026-08-01",
            target_sessions = 12,
            status = "ACTIVE",
            settled_at = null,
            updated_at = "2026-08-01T10:00:00"
        )
        cycleRepository.insertCycle(cycle)

        val classDay = Class_day(
            id = "class-1",
            student_id = "example-student-2",
            cycle_id = "cycle-1",
            date = LocalDate.now().toString(),
            slot = 1,
            status = "HELD",
            source = "markToday",
            note = null,
            created_at = timestamp,
            updated_at = timestamp
        )

        classDayRepository.insertClassDay(classDay)
        val result =
            classDayRepository.getClassDaysForStudent("example-student-2")
//        result.forEach {
//            Log.d("DB_TEST", it.toString())
//        }

    }

    fun getSchedulesForStudent(context: Context) {
        val database = TuitionDatabaseFactory.create(context)
        val scheduleRepository = StudentScheduleRepository(database)

        val schedule = Student_schedule(
            id = "schedule-1",
            student_id = "example-student-2",
            day_of_week = "MON",
            start_time = "17:00",
            duration = 90,
            updated_at = timestamp
        )

        scheduleRepository.insertStudentSchedule(schedule)

        val schedules = scheduleRepository.getSchedulesForStudent(
                "example-student-2"
            )

       // Log.d("DB_TEST", schedules.toString())
    }

    fun testSettlement(context: Context) {

        val database = TuitionDatabaseFactory.create(context)
        val studentRepository = StudentRepository(database)
        val cycleRepository = CycleRepository(database)
        val settlementRepository = SettlementRepository(database)

        val student = Student(
            id = "example-student-2",
            name = "Example Student",
            monthly_rate = 300000,
            cycle_length_sessions = 12,
            student_phone = null,
            guardian_name = null,
            guardian_phone = null,
            address = null,
            subject = null,
            created_at = timestamp,
            updated_at = timestamp,
            is_archived = 0
        )

        studentRepository.insertStudent(student)

        val cycle = Cycle(
            id = "cycle-1",
            student_id = "example-student-2",
            idx = 1,
            start_date = "2026-08-01",
            target_sessions = 12,
            status = "ACTIVE",
            settled_at = null,
            updated_at = timestamp
        )

        cycleRepository.insertCycle(cycle)

        val settlement = Settlement(
            id = "settlement-1",
            student_id = "example-student-2",
            cycle_id = "cycle-1",
            sessions_counted = 5,
            amount = 12000,
            payment_status = "DUE",
            collected_at = null,
            settled_at = LocalDateTime.now().toString(),
            updated_at = LocalDateTime.now().toString(),
            note = "Test settlement"
        )

        settlementRepository.insertSettlement(settlement)

        val result =
            settlementRepository.getSettlementByCycleId("cycle-1")

        Log.d("DB_TEST", result.toString())
    }
}