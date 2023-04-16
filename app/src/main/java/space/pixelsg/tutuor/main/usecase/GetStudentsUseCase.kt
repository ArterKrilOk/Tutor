package space.pixelsg.tutuor.main.usecase

import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.domain.models.StudentEntity
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentsRepo: StudentsRepository
) : UseCase<Unit, List<StudentEntity>> {
    override fun execute(input: Unit): Flow<List<StudentEntity>> = studentsRepo.getStudentsFlow()
}