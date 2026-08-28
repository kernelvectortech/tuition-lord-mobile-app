package com.kernelvector.tuitionlord.core.service

/**
 * BaseServiceImpl — Abstract base implementation of [BaseService].
 *
 * Provides a skeletal, in-memory implementation of all four CRUD operations
 * backed by a [MutableList]. Subclasses override individual methods to swap in
 * a real persistence layer (e.g. SQLDelight) while inheriting any common logic
 * defined here.
 *
 * ### Extension points
 * - Override [getAll] / [getById] / [save] / [delete] for persistence.
 * - Override [extractId] to tell the base class how to read the [ID] field from
 *   an entity — required by the default [save] and [delete] implementations.
 *
 * NO ANDROID IMPORTS — pure Kotlin, ready for KMP extraction.
 *
 * @param T  The entity type this service manages.
 * @param ID The type used to uniquely identify an entity (e.g. [String], [Long]).
 */
abstract class BaseServiceImpl<T, ID> : BaseService<T, ID> {

    /**
     * In-memory store used by the default (non-persisted) implementations.
     *
     * Subclasses that delegate to a real database do not need to interact with
     * this list; they may leave it empty and override all four CRUD methods.
     */
    protected val store: MutableList<T> = mutableListOf()

    // -------------------------------------------------------------------------
    // Abstract helpers
    // -------------------------------------------------------------------------

    /**
     * Extract the unique identifier from an entity instance.
     *
     * Subclasses must implement this so that [save] and [delete] can locate
     * entities in [store] without knowing the concrete type of [T].
     *
     * @param entity The entity whose ID should be extracted.
     * @return The unique identifier of [entity].
     */
    protected abstract fun extractId(entity: T): ID

    // -------------------------------------------------------------------------
    // BaseService default implementations
    // -------------------------------------------------------------------------

    /**
     * Returns a snapshot of all entities currently in [store].
     *
     * Override to load from a persistent data source instead.
     */
    override fun getAll(): List<T> = store.toList()

    /**
     * Finds the first entity in [store] whose extracted ID equals [id].
     *
     * Override to query a persistent data source instead.
     *
     * @param id The identifier to search for.
     * @return The matching entity, or `null` if not found.
     */
    override fun getById(id: ID): T? =
        store.firstOrNull { extractId(it) == id }

    /**
     * Upserts [entity] into [store]:
     * - Replaces an existing entry if an entity with the same ID is found.
     * - Appends [entity] to [store] if no match exists.
     *
     * Override to write through to a persistent data source instead.
     *
     * @param entity The entity to save or update.
     * @return The saved entity.
     */
    override fun save(entity: T): T {
        val index = store.indexOfFirst { extractId(it) == extractId(entity) }
        if (index >= 0) {
            store[index] = entity
        } else {
            store.add(entity)
        }
        return entity
    }

    /**
     * Removes the entity with the given [id] from [store].
     *
     * Override to delete from a persistent data source instead.
     *
     * @param id The identifier of the entity to remove.
     * @return `true` if an entity was removed; `false` if [id] was not found.
     */
    override fun delete(id: ID): Boolean =
        store.removeAll { extractId(it) == id }
}
