package space.pixelsg.tutuor.domain.repository

import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.domain.models.StudentEntity

interface StudentsRepository : Repository<Long, StudentEntity> {
    /**
     * Returns hot flow of students
     */
    fun getStudentsFlow(): Flow<List<StudentEntity>>
    fun getStudentFlow(id: Long): Flow<StudentEntity>
}