package space.pixelsg.tutuor.main.lessons.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.models.LessonEntity
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class EditLessonUseCase @Inject constructor(
    private val lessonRepository: LessonsRepository,
) : UseCase<EditLessonParams, EditLessonResult> {
    override fun execute(input: EditLessonParams) = flow {
        try {
            val oldLesson = lessonRepository.getById(input.id)
            val newLesson = oldLesson.copy(
                description = input.description,
                grade = input.grade
            )
            emit(
                EditLessonResult.Success(
                    lessonRepository.save(newLesson)
                )
            )
        } catch (e: Throwable) {
            emit(EditLessonResult.Failed(e))
        }
    }.flowOn(Dispatchers.IO)
}

data class EditLessonParams(
    val id: Long,
    val grade: Int,
    val description: String,
)

sealed class EditLessonResult {
    data class Success(val lesson: LessonEntity) : EditLessonResult()
    data class Failed(val t: Throwable) : EditLessonResult()
}