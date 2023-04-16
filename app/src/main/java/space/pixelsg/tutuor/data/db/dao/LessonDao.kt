package space.pixelsg.tutuor.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.data.db.enitities.LessonRoomEntity

@Dao
abstract class LessonDao : CommonLongEntityDao<LessonRoomEntity>() {
    @Query("SELECT * FROM lessons")
    abstract fun getLessonsFlow(): Flow<List<LessonRoomEntity>>

    @Query("SELECT * FROM lessons")
    abstract suspend fun getLessons(): List<LessonRoomEntity>

    @Query("SELECT * FROM lessons WHERE id=:id")
    abstract suspend fun getLesson(id: Long): LessonRoomEntity

    @Query("SELECT * FROM lessons WHERE id=:id")
    abstract fun getLessonFlow(id: Long): Flow<LessonRoomEntity>

    @Query("DELETE FROM lessons")
    abstract suspend fun clear()
}