package space.pixelsg.tutuor.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.data.db.enitities.TeacherRoomEntity

@Dao
abstract class TeacherDao : CommonLongEntityDao<TeacherRoomEntity>() {
    @Query("SELECT * FROM teachers")
    abstract fun getTeachersFlow(): Flow<List<TeacherRoomEntity>>

    @Query("SELECT * FROM teachers")
    abstract suspend fun getTeachers(): List<TeacherRoomEntity>

    @Query("SELECT * FROM teachers WHERE id=:id")
    abstract suspend fun getTeacher(id: Long): TeacherRoomEntity

    @Query("SELECT * FROM teachers WHERE id=:id")
    abstract fun getTeacherFlow(id: Long): Flow<TeacherRoomEntity>

    @Query("DELETE FROM teachers")
    abstract suspend fun clear()
}