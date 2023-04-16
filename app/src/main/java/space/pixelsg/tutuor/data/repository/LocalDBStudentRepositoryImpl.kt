package space.pixelsg.tutuor.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import space.pixelsg.tutuor.data.db.dao.StudentDao
import space.pixelsg.tutuor.data.db.enitities.StudentRoomEntity
import space.pixelsg.tutuor.data.di.DataScope
import space.pixelsg.tutuor.data.mapper.StudentMapper
import space.pixelsg.tutuor.domain.models.StudentEntity
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.mapper.map
import javax.inject.Inject

@DataScope
class LocalDBStudentRepositoryImpl @Inject constructor(
    private val studentDao: StudentDao
) : StudentsRepository {
    override fun getStudentsFlow(): Flow<List<StudentEntity>> =
        studentDao.getStudentsFlow().map { students ->
            students.map { it.map() }
        }

    override fun getStudentFlow(id: Long): Flow<StudentEntity> =
        studentDao.getStudentFlow(id).map { it.map() }

    override suspend fun save(entity: StudentEntity): StudentEntity {
        val id = studentDao.upsert(entity.map<StudentRoomEntity>(StudentMapper::class))
        return studentDao.getStudent(id).map()
    }

    override suspend fun remove(entity: StudentEntity): StudentEntity {
        studentDao.delete(entity.map())
        return entity
    }

    override suspend fun getAllRemote(): List<StudentEntity> =
        studentDao.getStudents().map { it.map() }

    override suspend fun getAllLocal(): List<StudentEntity> =
        studentDao.getStudents().map { it.map() }

    override suspend fun getById(id: Long): StudentEntity =
        studentDao.getStudent(id).map()

    override suspend fun clear() {
        studentDao.clear()
    }
}