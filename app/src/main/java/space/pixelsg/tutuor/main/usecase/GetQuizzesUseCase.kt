package space.pixelsg.tutuor.main.usecase

import kotlinx.coroutines.flow.Flow
import space.pixelsg.tutuor.domain.models.QuizEntity
import space.pixelsg.tutuor.domain.repository.QuizRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class GetQuizzesUseCase @Inject constructor(
    private val quizzesRepository: QuizRepository
) : UseCase<Unit, List<QuizEntity>> {
    override fun execute(input: Unit): Flow<List<QuizEntity>> = quizzesRepository.getQuizzesFlow()
}