package space.pixelsg.tutuor.domain.repository

import space.pixelsg.tutuor.domain.models.Entity

interface Repository<ID, T : Entity<ID>> {
    /**
     * Upsert element
     * update if exist, otherwise insert
     */
    suspend fun save(entity: T): T
    suspend fun remove(entity: T): T

    suspend fun getAllRemote(): List<T>
    suspend fun getAllLocal(): List<T>
    suspend fun getById(id: ID): T

    suspend fun clear()
}