package space.pixelsg.tutuor.main.create_student

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.take
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.usecase.CreateStudentParams
import space.pixelsg.tutuor.main.usecase.CreateStudentUseCase
import javax.inject.Inject

class CreateStudentViewModel @Inject constructor(
    private val createStudentUseCase: CreateStudentUseCase
) : CommonViewModel() {
    val fullName = MutableStateFlow("")
    val tg = MutableStateFlow("")
    val tel = MutableStateFlow("")
    val address = MutableStateFlow("")

    val isAddEnabled = combine(
        fullName,
        tg,
        tel,
        address
    ) { name, tg, tel, address ->
        name.isNotEmpty() && tg.isNotEmpty() && tel.isNotEmpty() && address.isNotEmpty()
    }.shareWhileSubscribed()

    fun crateUser() = combine(
        fullName.take(1),
        tg.take(1),
        tel.take(1),
        address.take(1)
    ) { name, tg, tel, address ->
        CreateStudentParams(name, tg, address, tel)
    }.flatMapConcat {
        createStudentUseCase.execute(it)
    }
}