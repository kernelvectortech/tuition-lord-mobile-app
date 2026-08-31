package com.kernelvector.tuitionlord

import android.app.Application
import com.kernelvector.tuitionlord.database.StudentRepository
import com.kernelvector.tuitionlord.database.TuitionDatabaseFactory

class TuitionLordApplication : Application() {
    val database by lazy { TuitionDatabaseFactory.create(this) }
    val studentRepository by lazy { StudentRepository(database) }
}