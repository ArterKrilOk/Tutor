package space.pixelsg.tutuor.auth.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.api.AuthApi
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.TeachersRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authApi: AuthApi,
    private val authRepository: AuthRepository,
    private val teachersRepo: TeachersRepository,
) : UseCase<AuthParams, AuthResponse> {
    override fun execute(input: AuthParams) = flow {
        try {
            val (token, teacher) = authApi.auth(input.telegram, input.password)
            teachersRepo.save(teacher)
            authRepository.setTokenAndTeacherID(token, teacher.id)
            emit(AuthResponse.Success)
        } catch (e: Throwable) {
            emit(AuthResponse.Failed(e))
        }
    }.flowOn(Dispatchers.IO)

}

data class AuthParams(
    val telegram: String,
    val password: String,
)

sealed class AuthResponse {
    object Success : AuthResponse()
    data class Failed(val t: Throwable) : AuthResponse()
}