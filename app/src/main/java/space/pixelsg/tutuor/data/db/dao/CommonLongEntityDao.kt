package space.pixelsg.tutuor.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import space.pixelsg.tutuor.data.db.enitities.RoomEntity

abstract class CommonLongEntityDao<E : RoomEntity<Long>> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(entity: E): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(entities: List<E>): List<Long>

    @Update
    abstract suspend fun update(entity: E)

    @Update
    abstract suspend fun update(entities: List<E>)

    @Delete
    abstract suspend fun delete(entity: E)

    @Transaction
    open suspend fun upsert(entity: E): Long {
        val id = insert(entity)
        if (id != -1L) return id
        update(entity)
        return entity.id ?: -1
    }

    @Transaction
    open suspend fun upsert(entities: List<E>): List<Long> {
        return entities.map { insert(it) to it }.map { (insert, entity) ->
            if (insert != -1L) insert
            else {
                update(entity)
                entity.id ?: -1
            }
        }
    }
}