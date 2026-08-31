package com.kernelvector.tuitionlord.core.service

/**
 * BaseService — Generic contract for all service layer interfaces.
 *
 * Defines the four fundamental CRUD operations parameterised by entity type [T]
 * and identifier type [ID]. All concrete service interfaces must extend this one.
 *
 * NO ANDROID IMPORTS — pure Kotlin, ready for KMP extraction.
 *
 * @param T  The entity type this service manages.
 * @param ID The type used to uniquely identify an entity (e.g. [String], [Long]).
 */
interface BaseService<T, ID> {

    /**
     * Retrieve every entity of type [T] known to this service.
     *
     * @return An immutable list of all entities; empty list if none exist.
     */
    fun getAll(): List<T>

    /**
     * Retrieve a single entity by its unique identifier.
     *
     * @param id The unique identifier of the entity to look up.
     * @return The matching entity, or `null` if no entity with that [id] exists.
     */
    fun getById(id: ID): T?

    /**
     * Persist a new entity or replace an existing one.
     *
     * @param entity The entity to save.
     * @return The saved entity (may differ from the input if the implementation
     *         enriches it, e.g. by assigning a generated ID).
     */
    fun save(entity: T): T

    /**
     * Remove the entity identified by [id] from the data store.
     *
     * @param id The unique identifier of the entity to delete.
     * @return `true` if an entity was found and deleted; `false` otherwise.
     */
    fun delete(id: ID): Boolean
}
