package space.pixelsg.tutuor.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.data.db.enitities.StudentRoomEntity

@Dao
abstract class StudentDao : CommonLongEntityDao<StudentRoomEntity>() {
    @Query("SELECT * FROM students")
    abstract fun getStudentsFlow(): Flow<List<StudentRoomEntity>>

    @Query("SELECT * FROM students")
    abstract suspend fun getStudents(): List<StudentRoomEntity>

    @Query("SELECT * FROM students WHERE id=:id")
    abstract suspend fun getStudent(id: Long): StudentRoomEntity

    @Query("SELECT * FROM students WHERE id=:id")
    abstract fun getStudentFlow(id: Long): Flow<StudentRoomEntity>

    @Query("SELECT * FROM students")
    abstract fun getStudentsPagingSource(): PagingSource<Int, StudentRoomEntity>

    @Query("DELETE FROM students")
    abstract suspend fun clear()
}