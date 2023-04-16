package space.pixelsg.tutuor.main.lessons.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import space.pixelsg.tutuor.domain.models.LessonEntity
import space.pixelsg.tutuor.domain.repository.LessonsRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class GetLessonUseCase @Inject constructor(
    private val lessonsRepository: LessonsRepository
) : UseCase<Long, LessonEntity> {
    override fun execute(input: Long) = lessonsRepository.getLessonFlow(input)
        .flowOn(Dispatchers.IO)

}