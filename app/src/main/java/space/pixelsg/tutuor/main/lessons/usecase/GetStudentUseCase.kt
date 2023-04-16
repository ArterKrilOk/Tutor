package space.pixelsg.tutuor.main.lessons.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.models.StudentEntity
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class GetStudentUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository
) : UseCase<Long, StudentEntity> {
    override fun execute(input: Long): Flow<StudentEntity> =
        studentsRepository.getStudentFlow(input).flowOn(Dispatchers.IO)
}