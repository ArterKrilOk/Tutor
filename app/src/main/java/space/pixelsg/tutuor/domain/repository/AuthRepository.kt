package space.pixelsg.tutuor.domain.repository

interface AuthRepository {
    suspend fun setTokenAndTeacherID(token: String, teacherID: Long)
    suspend fun getToken(): String
    suspend fun getTeacherID(): Long
    suspend fun isAuthed(): Boolean
    suspend fun clear()
}
