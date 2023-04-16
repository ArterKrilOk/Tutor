package space.pixelsg.tutuor.main.lessons.usecase

import space.pixelsg.tutuor.domain.models.LessonEntity
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class GetLessonsUseCase @Inject constructor(
    private val lessonsRepository: LessonsRepository
) : UseCase<Unit, List<LessonEntity>> {
    override fun execute(input: Unit) = lessonsRepository.getLessonsFlow()

}