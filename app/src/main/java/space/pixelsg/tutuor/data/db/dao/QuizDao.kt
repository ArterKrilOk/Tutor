package space.pixelsg.tutuor.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.data.db.enitities.QuizRoomEntity

@Dao
abstract class QuizDao : CommonLongEntityDao<QuizRoomEntity>() {
    @Query("SELECT * FROM quizzes")
    abstract fun getQuizzesFlow(): Flow<List<QuizRoomEntity>>

    @Query("SELECT * FROM quizzes")
    abstract suspend fun getQuizzes(): List<QuizRoomEntity>

    @Query("SELECT * FROM quizzes WHERE id=:id")
    abstract suspend fun getQuiz(id: Long): QuizRoomEntity

    @Query("SELECT * FROM quizzes WHERE id=:id")
    abstract fun getQuizFlow(id: Long): Flow<QuizRoomEntity>

    @Query("DELETE FROM quizzes")
    abstract suspend fun clear()
}