package space.pixelsg.tutuor.main.account

import kotlinx.coroutines.flow.catch
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.usecase.GetTeacherInfoUseCase
import space.pixelsg.tutuor.main.usecase.LogOutUseCase
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    getTeacherInfoUseCase: GetTeacherInfoUseCase,
    private val logOutUseCase: LogOutUseCase,
) : CommonViewModel() {
    val teacherInfo = getTeacherInfoUseCase.execute(Unit)
        .catch { error.emit(it.localizedMessage ?: it.message ?: "Something vent wrong") }
        .shareWhileSubscribed()


    fun logOut() = logOutUseCase.execute(Unit)
        .catch { error.emit(it.localizedMessage ?: it.message ?: "Something vent wrong") }
}