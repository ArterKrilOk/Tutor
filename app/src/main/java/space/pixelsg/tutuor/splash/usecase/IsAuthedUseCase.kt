package space.pixelsg.tutuor.splash.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class IsAuthedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<Unit, Boolean> {
    override fun execute(input: Unit): Flow<Boolean> = flow {
        emit(authRepository.isAuthed())
    }.flowOn(Dispatchers.IO)
}