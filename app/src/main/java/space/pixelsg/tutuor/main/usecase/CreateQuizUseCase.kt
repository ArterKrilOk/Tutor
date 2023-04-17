package space.pixelsg.tutuor.main.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.joda.time.DateTime
import space.pixelsg.tutuor.domain.models.QuizEntity
import space.pixelsg.tutuor.domain.repository.QuizRepository
import space.pixelsg.tutuor.domain.usecase.UseCase
import javax.inject.Inject

class CreateQuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository,
) : UseCase<CreateQuizParams, CreateQuizResult> {
    override fun execute(input: CreateQuizParams) = flow {
        try {
            val quiz = quizRepository.save(
                QuizEntity(
                    id = -1, //Set ID to -1 to create new
                    title = input.title,
                    url = input.url,
                    dateTime = DateTime(),
                )
            )
            emit(CreateQuizResult.Success(quiz))
        } catch (e: Throwable) {
            emit(CreateQuizResult.Failed(e))
        }
    }.flowOn(Dispatchers.IO)
}

data class CreateQuizParams(
    val title: String,
    val url: String,
)

sealed class CreateQuizResult {
    data class Success(val quiz: QuizEntity) : CreateQuizResult()
    data class Failed(val t: Throwable) : CreateQuizResult()
}