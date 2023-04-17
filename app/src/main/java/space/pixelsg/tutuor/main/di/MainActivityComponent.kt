package space.pixelsg.tutuor.main.di

import dagger.Component
import space.pixelsg.tutuor.data.di.DataComponent
import space.pixelsg.tutuor.main.account.AccountSubcomponent
import space.pixelsg.tutuor.main.create_quiz.CreateQuizSubcomponent
import space.pixelsg.tutuor.main.create_student.CreateStudentSubcomponent
import space.pixelsg.tutuor.main.lessons.di.LessonsSubcomponent
import space.pixelsg.tutuor.main.quizes.QuizzesSubcomponent
import space.pixelsg.tutuor.main.student_info.StudentInfoSubcomponent
import space.pixelsg.tutuor.main.students.StudentsSubcomponent
import space.pixelsg.tutuor.main.usecase.CreateQuizUseCase
import space.pixelsg.tutuor.main.usecase.CreateStudentUseCase
import space.pixelsg.tutuor.main.usecase.GetQuizzesUseCase
import space.pixelsg.tutuor.main.usecase.GetStudentUseCase
import space.pixelsg.tutuor.main.usecase.GetTeacherInfoUseCase
import space.pixelsg.tutuor.main.usecase.LogOutUseCase

@MainActivityScope
@Component(
    dependencies = [DataComponent::class]
)
interface MainActivityComponent {
    val createStudentsUseCase: CreateStudentUseCase
    val getStudentsUseCase: CreateStudentUseCase
    val getStudentUseCase: GetStudentUseCase
    val getTeacherInfoUseCase: GetTeacherInfoUseCase
    val logOutUseCase: LogOutUseCase
    val getQuizzesUseCase: GetQuizzesUseCase
    val createQuizUseCase: CreateQuizUseCase

    val studentsSubcomponent: StudentsSubcomponent
    val createStudentSubcomponent: CreateStudentSubcomponent
    val studentInfoSubcomponent: StudentInfoSubcomponent
    val accountSubcomponent: AccountSubcomponent
    val quizzesSubcomponent: QuizzesSubcomponent
    val createQuizSubcomponent: CreateQuizSubcomponent

    val lessonsSubcomponent: LessonsSubcomponent
}