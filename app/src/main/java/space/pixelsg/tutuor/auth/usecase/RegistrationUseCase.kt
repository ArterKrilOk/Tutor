package space.pixelsg.tutuor.auth.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.api.AuthApi
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.TeachersRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authApi: AuthApi,
    private val authRepository: AuthRepository,
    private val teachersRepo: TeachersRepository,
) : UseCase<RegistrationParams, RegistrationResponse> {
    override fun execute(input: RegistrationParams): Flow<RegistrationResponse> = flow {
        try {
            //Run auth request
            val (token, teacher) = authApi.register(
                fullName = input.fullName,
                telegram = input.telegram,
                email = input.email,
                password = input.password
            )
            //Save teacher object locally
            teachersRepo.save(teacher)
            //Save token
            authRepository.setTokenAndTeacherID(token, teacher.id)
            emit(RegistrationResponse.Success)
        } catch (e: Throwable) {
            emit(RegistrationResponse.Failed(e))
        }
    }.flowOn(Dispatchers.IO)
}

data class RegistrationParams(
    val fullName: String,
    val telegram: String,
    val email: String,
    val password: String
)

sealed class RegistrationResponse {
    object Success : RegistrationResponse()
    data class Failed(val t: Throwable) : RegistrationResponse()
}