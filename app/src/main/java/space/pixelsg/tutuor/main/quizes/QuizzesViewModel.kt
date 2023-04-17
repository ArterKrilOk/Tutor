package space.pixelsg.tutuor.main.quizes

import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.usecase.GetQuizzesUseCase
import javax.inject.Inject

class QuizzesViewModel @Inject constructor(
    getQuizzesUseCase: GetQuizzesUseCase
) : CommonViewModel() {
    val items = getQuizzesUseCase.execute(Unit)
        .shareWhileSubscribed()
}