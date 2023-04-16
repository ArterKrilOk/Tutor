package space.pixelsg.tutuor.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.joda.time.LocalDate
import space.pixelsg.tutuor.data.db.dao.LessonDao
import space.pixelsg.tutuor.data.db.enitities.LessonRoomEntity
import space.pixelsg.tutuor.data.di.DataScope
import space.pixelsg.tutuor.data.mapper.LessonMapper
import space.pixelsg.tutuor.domain.models.LessonEntity
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.mapper.map
import javax.inject.Inject

@DataScope
class LocalDBLessonRepositoryImpl @Inject constructor(
    private val lessonDao: LessonDao
) : LessonsRepository {
    override fun getLessonsFlow(): Flow<List<LessonEntity>> =
        lessonDao.getLessonsFlow().map { lessons ->
            lessons.map { it.map() }
        }

    override fun getLessonFlow(id: Long): Flow<LessonEntity> =
        lessonDao.getLessonFlow(id).map { it.map() }

    override suspend fun getLessons(date: LocalDate): List<LessonEntity> =
        lessonDao.getLessons()
            .filter { it.date.toLocalDate() == date }
            .map { it.map() }

    override suspend fun save(entity: LessonEntity): LessonEntity {
        val id = lessonDao.upsert(entity.map<LessonRoomEntity>(LessonMapper::class))
        return lessonDao.getLesson(id).map()
    }

    override suspend fun remove(entity: LessonEntity): LessonEntity {
        lessonDao.delete(entity.map())
        return entity
    }

    override suspend fun getAllRemote(): List<LessonEntity> =
        lessonDao.getLessons().map { it.map() }

    override suspend fun getAllLocal(): List<LessonEntity> =
        lessonDao.getLessons().map { it.map() }

    override suspend fun getById(id: Long): LessonEntity =
        lessonDao.getLesson(id).map()

    override suspend fun clear() {
        lessonDao.clear()
    }
}