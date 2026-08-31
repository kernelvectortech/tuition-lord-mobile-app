package com.kernelvector.tuitionlord.core.service

/**
 * In-memory [BaseService] implementation used until the SQLDelight repositories
 * are wired in. Subclasses supply [extractId] and override the CRUD methods one
 * by one as real persistence lands.
 *
 * NO ANDROID IMPORTS - pure Kotlin, ready for KMP extraction.
 */
abstract class BaseServiceImpl<T, ID> : BaseService<T, ID> {

    protected val store: MutableList<T> = mutableListOf()

    protected abstract fun extractId(entity: T): ID

    override fun getAll(): List<T> = store.toList()

    override fun getById(id: ID): T? =
        store.firstOrNull { extractId(it) == id }

    override fun save(entity: T): T {
        val index = store.indexOfFirst { extractId(it) == extractId(entity) }
        if (index >= 0) {
            store[index] = entity
        } else {
            store.add(entity)
        }
        return entity
    }

    override fun delete(id: ID): Boolean =
        store.removeAll { extractId(it) == id }
}
