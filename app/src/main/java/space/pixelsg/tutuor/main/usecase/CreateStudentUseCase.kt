package space.pixelsg.tutuor.main.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.models.StudentEntity
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class CreateStudentUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val studentsRepo: StudentsRepository,
) : UseCase<CreateStudentParams, CreateStudentResult> {
    override fun execute(input: CreateStudentParams) = flow {
        try {
            val teacherID = authRepository.getTeacherID()
            val student = studentsRepo.save(
                StudentEntity(
                    id = -1, //Set ID to -1 to create new
                    fullName = input.fullName,
                    address = input.address,
                    telegram = input.telegram,
                    telNumber = input.telNumber,
                    teacherID = teacherID
                )
            )
            emit(CreateStudentResult.Success(student))
        } catch (e: Throwable) {
            emit(CreateStudentResult.Failed(e))
        }
    }.flowOn(Dispatchers.IO)
}

data class CreateStudentParams(
    val fullName: String,
    val telegram: String,
    val address: String,
    val telNumber: String
)

sealed class CreateStudentResult {
    data class Success(val student: StudentEntity) : CreateStudentResult()
    data class Failed(val t: Throwable) : CreateStudentResult()
}