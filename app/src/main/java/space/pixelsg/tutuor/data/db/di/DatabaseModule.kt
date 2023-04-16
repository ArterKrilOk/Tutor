package space.pixelsg.tutuor.data.db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import space.pixelsg.tutuor.data.db.dao.LessonDao
import space.pixelsg.tutuor.data.db.dao.StudentDao
import space.pixelsg.tutuor.data.db.dao.TeacherDao
import space.pixelsg.tutuor.data.db.database.AppDatabase
import space.pixelsg.tutuor.data.di.DataScope

@Module
class DatabaseModule {
    @Provides
    @DataScope
    fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @DataScope
    fun provideTeacherDao(database: AppDatabase): TeacherDao = database.teacherDao()

    @Provides
    @DataScope
    fun provideStudentDao(database: AppDatabase): StudentDao = database.studentDao()

    @Provides
    @DataScope
    fun provideLessonDao(database: AppDatabase): LessonDao = database.lessonDao()
}