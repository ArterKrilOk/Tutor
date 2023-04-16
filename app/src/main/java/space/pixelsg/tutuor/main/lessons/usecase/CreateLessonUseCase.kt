package space.pixelsg.tutuor.main.lessons.usecase

import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime
import space.pixelsg.tutuor.domain.models.LessonEntity
import space.pixelsg.tutuor.domain.repository.AuthRepository
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class CreateLessonUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val lessonRepository: LessonsRepository,
) : UseCase<CreateLessonParams, CreateLessonResult> {
    override fun execute(input: CreateLessonParams) = flow {
        try {
            val teacherID = authRepository.getTeacherID()
            val lesson = lessonRepository.save(
                LessonEntity(
                    id = -1,    //Set ID to -1 to create new
                    grade = -1, //No grade
                    title = input.title,
                    description = input.description,
                    date = input.date,
                    studentID = input.studentID,
                    teacherID = teacherID
                )
            )
            emit(CreateLessonResult.Success(lesson))
        } catch (e: Throwable) {
            emit(CreateLessonResult.Failed(e))
        }
    }
}

data class CreateLessonParams(
    val title: String,
    val description: String,
    val date: DateTime,
    val studentID: Long
)

sealed class CreateLessonResult {
    data class Success(val lesson: LessonEntity) : CreateLessonResult()
    data class Failed(val t: Throwable) : CreateLessonResult()
}