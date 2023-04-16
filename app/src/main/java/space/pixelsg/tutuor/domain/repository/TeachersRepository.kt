package space.pixelsg.tutuor.domain.repository

import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.domain.models.TeacherEntity

interface TeachersRepository : Repository<Long, TeacherEntity> {
    fun getTeachersFlow(): Flow<List<TeacherEntity>>
    fun getTeacherFlow(id: Long): Flow<TeacherEntity>
}