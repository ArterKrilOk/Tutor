package space.pixelsg.tutuor.data.di

import dagger.Component
import space.pixelsg.tutuor.app.di.AppComponent
import space.pixelsg.tutuor.domain.api.AuthApi
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.domain.repository.QuizRepository
import space.pixelsg.tutuor.domain.repository.StudentsRepository
import space.pixelsg.tutuor.domain.repository.TeachersRepository

@DataScope
@Component(
    dependencies = [AppComponent::class],
    modules = [DataModule::class]
)
interface DataComponent {
    val teachersRepository: TeachersRepository
    val lessonsRepository: LessonsRepository
    val studentRepository: StudentsRepository
    val authRepository: AuthRepository
    val quizRepository: QuizRepository

    val authApi: AuthApi
}