package com.kernelvector.tuitionlord.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

/**
 * Creates a throwaway in-memory database for a single test, with foreign keys
 * enforced the same way [TuitionDatabaseFactory] enforces them on device.
 */
fun createInMemoryDatabase(): TuitionDatabase {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    TuitionDatabase.Schema.create(driver)
    driver.execute(null, "PRAGMA foreign_keys = ON", 0)
    return TuitionDatabase(driver)
}

fun student(
    id: String,
    name: String = "Student $id",
    monthlyRate: Long = 300000,
    isArchived: Long = 0,
    updatedAt: String = "2026-01-01T09:00:00",
) = Student(
    id = id,
    name = name,
    monthly_rate = monthlyRate,
    cycle_length_sessions = 12,
    student_phone = null,
    guardian_name = null,
    guardian_phone = null,
    address = null,
    subject = null,
    created_at = "2026-01-01T09:00:00",
    updated_at = updatedAt,
    is_archived = isArchived,
)

fun cycle(
    id: String,
    studentId: String,
    idx: Long = 1,
    status: String = "ACTIVE",
    settledAt: String? = null,
) = Cycle(
    id = id,
    student_id = studentId,
    idx = idx,
    start_date = "2026-01-01",
    target_sessions = 12,
    status = status,
    settled_at = settledAt,
    updated_at = "2026-01-01T09:00:00",
)

fun classDay(
    id: String,
    studentId: String,
    cycleId: String?,
    date: String = "2026-01-05",
    slot: Long = 1,
    status: String = "SCHEDULED",
) = Class_day(
    id = id,
    student_id = studentId,
    cycle_id = cycleId,
    date = date,
    slot = slot,
    status = status,
    source = "markToday",
    note = null,
    created_at = "2026-01-05T09:00:00",
    updated_at = "2026-01-05T09:00:00",
)

fun schedule(
    id: String,
    studentId: String,
    dayOfWeek: String = "MON",
    startTime: String = "17:00",
    duration: Long = 90,
) = Student_schedule(
    id = id,
    student_id = studentId,
    day_of_week = dayOfWeek,
    start_time = startTime,
    duration = duration,
    updated_at = "2026-01-01T09:00:00",
)

fun settlement(
    id: String,
    studentId: String,
    cycleId: String,
    amount: Long = 300000,
    paymentStatus: String = "DUE",
) = Settlement(
    id = id,
    student_id = studentId,
    cycle_id = cycleId,
    sessions_counted = 12,
    amount = amount,
    payment_status = paymentStatus,
    collected_at = null,
    settled_at = "2026-02-01T09:00:00",
    updated_at = "2026-02-01T09:00:00",
    note = null,
)
