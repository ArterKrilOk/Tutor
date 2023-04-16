package space.pixelsg.tutuor.domain.api

import space.pixelsg.tutuor.domain.models.TeacherEntity

interface AuthApi {
    suspend fun register(
        fullName: String,
        email: String,
        telegram: String,
        password: String,
    ): AuthResponse

    suspend fun auth(
        telegram: String,
        password: String
    ): AuthResponse
}

data class AuthResponse(
    val token: String,
    val teacher: TeacherEntity
)