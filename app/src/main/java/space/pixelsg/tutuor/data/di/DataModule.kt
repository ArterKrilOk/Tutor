package space.pixelsg.tutuor.data.di

import dagger.Binds
import dagger.Module
import space.pixelsg.tutuor.data.api.TestAuthApi
import space.pixelsg.tutuor.data.db.di.DatabaseModule
import space.pixelsg.tutuor.data.repository.LocalDBAuthRepositoryImpl
import space.pixelsg.tutuor.data.repository.LocalDBLessonRepositoryImpl
import space.pixelsg.tutuor.data.repository.LocalDBStudentRepositoryImpl
import space.pixelsg.tutuor.data.repository.LocalDBTeacherRepositoryImpl
import space.pixelsg.tutuor.domain.api.AuthApi
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.domain.repository.TeachersRepository

@Module(
    includes = [DatabaseModule::class]
)
interface DataModule {
    @Binds
    @DataScope
    fun bindTeachersRepository(
        repo: LocalDBTeacherRepositoryImpl
    ): TeachersRepository

    @Binds
    @DataScope
    fun bindStudentsRepository(
        repo: LocalDBStudentRepositoryImpl
    ): StudentsRepository

    @Binds
    @DataScope
    fun bindLessonRepository(
        repo: LocalDBLessonRepositoryImpl
    ): LessonsRepository

    @Binds
    @DataScope
    fun bindAuthApi(
        api: TestAuthApi
    ): AuthApi

    @Binds
    @DataScope
    fun bindAuthRepository(
        repo: LocalDBAuthRepositoryImpl
    ): AuthRepository
}