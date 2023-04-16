package space.pixelsg.tutuor.main.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.domain.repository.TeachersRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class GetTeacherInfoUseCase @Inject constructor(
    private val teachersRepository: TeachersRepository,
    private val studentsRepository: StudentsRepository,
    private val authRepository: AuthRepository
) : UseCase<Unit, TeacherInfoResponse> {
    override fun execute(input: Unit): Flow<TeacherInfoResponse> = flow {
        emit(authRepository.getTeacherID())
    }.flatMapConcat {
        teachersRepository.getTeacherFlow(it)
    }.combine(studentsRepository.getStudentsFlow()) { teacher, students ->
        TeacherInfoResponse(
            teacher.id, teacher.fullName, teacher.email, teacher.telegram, students.size
        )
    }.flowOn(Dispatchers.IO)
}

data class TeacherInfoResponse(
    val id: Long,
    val fullName: String,
    val email: String,
    val telegram: String,
    val studentsCount: Int
)