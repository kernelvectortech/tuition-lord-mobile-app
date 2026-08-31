package com.kernelvector.tuitionlord.database

interface BaseRepository<T> {
    fun getByID(id: String): T?
    fun insert(item: T)
}