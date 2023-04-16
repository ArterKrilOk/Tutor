package space.pixelsg.tutuor.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joda.time.LocalDate
import space.pixelsg.tutuor.domain.models.LessonEntity

interface LessonsRepository : Repository<Long, LessonEntity> {
    /**
     * Returns hot flow of lessons
     */
    fun getLessonsFlow(): Flow<List<LessonEntity>>
    fun getLessonFlow(id: Long): Flow<LessonEntity>
    suspend fun getLessons(date: LocalDate): List<LessonEntity>
}