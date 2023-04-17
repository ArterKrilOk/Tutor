package space.pixelsg.tutuor.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import space.pixelsg.tutuor.data.db.dao.LessonDao
import space.pixelsg.tutuor.data.db.dao.QuizDao
import space.pixelsg.tutuor.data.db.dao.StudentDao
import space.pixelsg.tutuor.data.db.dao.TeacherDao
import space.pixelsg.tutuor.data.db.enitities.LessonRoomEntity
import space.pixelsg.tutuor.data.db.enitities.QuizRoomEntity
import space.pixelsg.tutuor.data.db.enitities.StudentRoomEntity
import space.pixelsg.tutuor.data.db.enitities.TeacherRoomEntity

@Database(
    entities = [
        TeacherRoomEntity::class,
        StudentRoomEntity::class,
        LessonRoomEntity::class,
        QuizRoomEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teacherDao(): TeacherDao
    abstract fun studentDao(): StudentDao
    abstract fun lessonDao(): LessonDao

    abstract fun quizDao(): QuizDao
}