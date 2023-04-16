package space.pixelsg.tutuor.main.students

import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.usecase.GetStudentsUseCase
import javax.inject.Inject

class StudentsViewModel @Inject constructor(
    getStudentsUseCase: GetStudentsUseCase,
) : CommonViewModel() {
    val students = getStudentsUseCase.execute(Unit)
        .shareWhileSubscribed()
}