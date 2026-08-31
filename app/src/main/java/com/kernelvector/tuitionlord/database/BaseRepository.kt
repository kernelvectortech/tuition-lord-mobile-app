package com.kernelvector.tuitionlord.database

/**
 * Generic contract for every SQLDelight-backed repository.
 * Mirrors [com.kernelvector.tuitionlord.core.service.BaseService] so the service
 * layer can delegate through without renaming anything.
 *
 * [T] is the generated row type, [ID] its primary key type.
 */
interface BaseRepository<T, ID> {

    fun getAll(): List<T>

    fun getById(id: ID): T?

    /** Fails on a duplicate primary key or UNIQUE violation - use [update] to modify a row. */
    fun insert(item: T)

    /** Overwrites the mutable columns of the row sharing [item]'s primary key; no-op if absent. */
    fun update(item: T)

    /** No-op if no row has that [id]. */
    fun delete(id: ID)

    fun exists(id: ID): Boolean = getById(id) != null
}
