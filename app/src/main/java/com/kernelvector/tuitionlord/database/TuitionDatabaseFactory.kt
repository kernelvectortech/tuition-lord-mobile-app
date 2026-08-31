package com.kernelvector.tuitionlord.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

object TuitionDatabaseFactory {
    fun create(context: Context): TuitionDatabase {
        val driver = AndroidSqliteDriver(
            schema = TuitionDatabase.Schema,
            context = context.applicationContext,
            name = "tuition.db"
        )

        driver.execute(null, "PRAGMA foreign_keys = ON", 0)
        return TuitionDatabase(driver)
    }
}