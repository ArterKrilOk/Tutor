package space.pixelsg.tutuor.main.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.domain.repository.TeachersRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val lessonsRepository: LessonsRepository,
    private val teachersRepository: TeachersRepository,
    private val studentsRepository: StudentsRepository
) : UseCase<Unit, Unit> {
    override fun execute(input: Unit): Flow<Unit> = flow {
        studentsRepository.clear()
        teachersRepository.clear()
        lessonsRepository.clear()
        authRepository.clear()
        emit(Unit)
    }.flowOn(Dispatchers.IO)

}