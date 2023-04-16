package space.pixelsg.tutuor.auth.ui.sign_in

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.take
import space.pixelsg.tutuor.auth.usecase.AuthParams
import space.pixelsg.tutuor.auth.usecase.AuthUseCase
import space.pixelsg.tutuor.common.CommonViewModel
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : CommonViewModel() {
    val tg = MutableStateFlow("")
    val password = MutableStateFlow("")

    val isSignInAvailable = combine(
        tg, password
    ) { tg, password ->
        tg.isNotEmpty() && password.isNotEmpty()
    }.shareWhileSubscribed()

    fun signIn() = combine(
        tg.take(1),
        password.take(1)
    ) { tg, password ->
        AuthParams(tg, password)
    }.flatMapConcat {
        authUseCase.execute(it)
    }
}