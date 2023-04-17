package space.pixelsg.tutuor.main.create_quiz

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.take
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.usecase.CreateQuizParams
import space.pixelsg.tutuor.main.usecase.CreateQuizUseCase
import javax.inject.Inject

@OptIn(FlowPreview::class)
class CreateQuizViewModel @Inject constructor(
    private val createQuizUseCase: CreateQuizUseCase
) : CommonViewModel() {
    val title = MutableStateFlow("")
    val url = MutableStateFlow("")

    val isAddEnabled = combine(
        title,
        url,
    ) { title, url ->
        title.isNotEmpty() && url.isNotEmpty()
    }.shareWhileSubscribed()

    fun createQuiz() = combine(
        title.take(1),
        url.take(1),
    ) { title, url ->
        CreateQuizParams(title, url)
    }.flatMapConcat {
        createQuizUseCase.execute(it)
    }
}