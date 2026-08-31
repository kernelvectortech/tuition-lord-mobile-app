package com.kernelvector.tuitionlord.core.service

/**
 * Generic CRUD contract for the service layer, parallel to
 * [com.kernelvector.tuitionlord.database.BaseRepository].
 *
 * NO ANDROID IMPORTS - pure Kotlin, ready for KMP extraction.
 *
 * [T] is the domain entity, [ID] its identifier type.
 */
interface BaseService<T, ID> {

    fun getAll(): List<T>

    fun getById(id: ID): T?

    /**
     * Upsert. Unlike the repository, which splits insert/update, the service
     * accepts either case.
     *
     * @return the stored entity, which may differ from [entity] if the
     *         implementation enriches it (e.g. a generated id).
     */
    fun save(entity: T): T

    /** @return `true` if an entity was found and deleted. */
    fun delete(id: ID): Boolean

    fun exists(id: ID): Boolean = getById(id) != null
}
