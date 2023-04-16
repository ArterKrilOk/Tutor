package space.pixelsg.tutuor.data.repository

import android.content.Context
import space.pixelsg.tutuor.domain.repository.AuthRepository
import javax.inject.Inject

class LocalDBAuthRepositoryImpl @Inject constructor(
    context: Context
) : AuthRepository {
    private val sharedPrefs by lazy {
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    override suspend fun setTokenAndTeacherID(token: String, teacherID: Long) {
        sharedPrefs.edit().apply {
            putString(Token, token)
            putLong(TID, teacherID)
            apply()
        }
    }

    override suspend fun getToken(): String {
        return sharedPrefs.getString(Token, "") ?: ""
    }

    override suspend fun getTeacherID(): Long {
        return sharedPrefs.getLong(TID, -1)
    }

    override suspend fun isAuthed(): Boolean {
        val token = getToken()
        val tid = getTeacherID()
        return token != "" && tid != -1L
    }

    override suspend fun clear() {
        sharedPrefs.edit().apply {
            putString(Token, "")
            putLong(TID, -1)
            apply()
        }
    }


    companion object {
        private const val Token = "token"
        private const val TID = "tid"
    }
}