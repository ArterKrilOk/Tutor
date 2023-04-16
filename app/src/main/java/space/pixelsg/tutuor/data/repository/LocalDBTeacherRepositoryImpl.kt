package space.pixelsg.tutuor.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.pixelsg.tutuor.data.db.dao.TeacherDao
import space.pixelsg.tutuor.data.db.enitities.TeacherRoomEntity
import space.pixelsg.tutuor.data.di.DataScope
import space.pixelsg.tutuor.data.mapper.TeacherMapper
import space.pixelsg.tutuor.domain.models.TeacherEntity
import space.pixelsg.tutuor.domain.repository.TeachersRepository
import space.pixelsg.tutuor.mapper.map
import javax.inject.Inject

@DataScope
class LocalDBTeacherRepositoryImpl @Inject constructor(
    private val teacherDao: TeacherDao,
) : TeachersRepository {
    override fun getTeachersFlow(): Flow<List<TeacherEntity>> =
        teacherDao.getTeachersFlow().map { teachers ->
            teachers.map { it.map() }
        }

    override fun getTeacherFlow(id: Long): Flow<TeacherEntity> =
        teacherDao.getTeacherFlow(id).map {
            it.map()
        }

    override suspend fun save(entity: TeacherEntity): TeacherEntity {
        val id = teacherDao.upsert(entity.map<TeacherRoomEntity>(TeacherMapper::class))
        return teacherDao.getTeacher(id).map()
    }

    override suspend fun remove(entity: TeacherEntity): TeacherEntity {
        teacherDao.delete(entity.map())
        return entity
    }

    override suspend fun getAllRemote(): List<TeacherEntity> =
        teacherDao.getTeachers().map { it.map() }

    override suspend fun getAllLocal(): List<TeacherEntity> =
        teacherDao.getTeachers().map { it.map() }

    override suspend fun getById(id: Long): TeacherEntity =
        teacherDao.getTeacher(id).map()

    override suspend fun clear() {
        teacherDao.clear()
    }
}