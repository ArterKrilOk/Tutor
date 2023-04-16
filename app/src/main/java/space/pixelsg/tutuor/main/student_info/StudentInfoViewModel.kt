package space.pixelsg.tutuor.main.student_info

import kotlinx.coroutines.flow.catch
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.usecase.GetStudentUseCase
import javax.inject.Inject

class StudentInfoViewModel @Inject constructor(
    private val getStudentUseCase: GetStudentUseCase
) : CommonViewModel() {

    fun getStudent(id: Long) = getStudentUseCase.execute(id)
        .catch { error.emit(it.localizedMessage ?: it.message ?: "Something went wrong") }
        .shareWhileSubscribed()
}