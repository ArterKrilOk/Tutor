package space.pixelsg.tutuor.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.pixelsg.tutuor.data.db.dao.QuizDao
import space.pixelsg.tutuor.data.db.enitities.QuizRoomEntity
import space.pixelsg.tutuor.data.di.DataScope
import space.pixelsg.tutuor.data.mapper.QuizzesMapper
import space.pixelsg.tutuor.domain.models.QuizEntity
import space.pixelsg.tutuor.domain.repository.QuizRepository
import space.pixelsg.tutuor.mapper.map
import javax.inject.Inject

@DataScope
class LocalDBQuizRepositoryImpl @Inject constructor(
    private val quizDao: QuizDao
) : QuizRepository {
    override fun getQuizzesFlow(): Flow<List<QuizEntity>> =
        quizDao.getQuizzesFlow().map { quizzes ->
            quizzes.map { it.map() }
        }

    override fun getQuizFlow(id: Long): Flow<QuizEntity> =
        quizDao.getQuizFlow(id).map {
            it.map()
        }

    override suspend fun save(entity: QuizEntity): QuizEntity {
        val id = quizDao.upsert(entity.map<QuizRoomEntity>(QuizzesMapper::class))
        return quizDao.getQuiz(id).map()
    }

    override suspend fun remove(entity: QuizEntity): QuizEntity {
        quizDao.delete(entity.map())
        return entity
    }

    override suspend fun getAllRemote(): List<QuizEntity> =
        quizDao.getQuizzes().map { it.map() }

    override suspend fun getAllLocal(): List<QuizEntity> =
        quizDao.getQuizzes().map { it.map() }

    override suspend fun getById(id: Long): QuizEntity =
        quizDao.getQuiz(id).map()

    override suspend fun clear() {
        quizDao.clear()
    }
}