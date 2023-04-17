package space.pixelsg.tutuor.domain.repository

import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.domain.models.QuizEntity

interface QuizRepository : Repository<Long, QuizEntity> {
    fun getQuizzesFlow(): Flow<List<QuizEntity>>
    fun getQuizFlow(id: Long): Flow<QuizEntity>
}