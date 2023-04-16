package space.pixelsg.tutuor.data.api

import space.pixelsg.tutuor.data.db.dao.TeacherDao
import space.pixelsg.tutuor.data.db.enitities.TeacherRoomEntity
import space.pixelsg.tutuor.data.di.DataScope
import space.pixelsg.tutuor.domain.api.AuthApi
import space.pixelsg.tutuor.domain.api.AuthResponse
import space.pixelsg.tutuor.mapper.map
import javax.inject.Inject

@DataScope
class TestAuthApi @Inject constructor(private val teacherDao: TeacherDao) : AuthApi {
    override suspend fun register(
        fullName: String,
        email: String,
        telegram: String,
        password: String
    ): AuthResponse {
        val teacherID = teacherDao.upsert(
            TeacherRoomEntity(
                id = null,
                fullName = fullName,
                telegram = telegram,
                email = email
            )
        )
        val teacher = teacherDao.getTeacher(teacherID)
        return AuthResponse("ASDasdasd", teacher.map())
    }

    override suspend fun auth(telegram: String, password: String): AuthResponse {
        val teacher = teacherDao.getTeacher(0)
        return AuthResponse("ASDasdasd", teacher.map())
    }
}