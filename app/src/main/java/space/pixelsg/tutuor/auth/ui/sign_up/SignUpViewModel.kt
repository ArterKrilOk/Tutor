package space.pixelsg.tutuor.auth.ui.sign_up

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import space.pixelsg.tutuor.auth.usecase.RegistrationParams
import space.pixelsg.tutuor.auth.usecase.RegistrationUseCase
import space.pixelsg.tutuor.common.CommonViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : CommonViewModel() {
    private val strongPasswordRegex =
        "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$".toRegex()
    private val validEmail = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex()

    val name = MutableStateFlow("")
    val email = MutableStateFlow("")
    val telegram = MutableStateFlow("")

    val password = MutableStateFlow("")
    val rePassword = MutableStateFlow("")

    private val isPasswordSame = combine(password, rePassword) { pass, rePass ->
        pass == rePass
    }.shareWhileSubscribed()

    private val isSomethingEmpty = combine(
        name,
        email,
        telegram,
        password
    ) { name, email, telegram, pass ->
        name.isNullOrEmpty() || email.isNullOrEmpty() || telegram.isNullOrEmpty() || pass.isNullOrEmpty()
    }.shareWhileSubscribed()

    private val isStrongPassword = password.map {
        it.matches(strongPasswordRegex)
    }.shareWhileSubscribed()

    private val isTelegram = telegram.map {
        it.startsWith("@")
    }.shareWhileSubscribed()

    private val isEmail = email.map {
        it.matches(validEmail)
    }.shareWhileSubscribed()

    val validation = combine(
        isSomethingEmpty,
        isEmail,
        isTelegram,
        isStrongPassword,
        isPasswordSame
    ) { empty, email, tg, pass, passRe ->
        ValidationResults(empty, !email, !tg, !pass, !passRe)
    }.shareWhileSubscribed()

    val successValidation = validation.map {
        !it.invalidEmail && !it.invalidTelegram && !it.somethingEmpty && !it.badPassword && !it.passwordsNotSame
    }.shareWhileSubscribed()

    fun signUp() = combine(
        name.take(1),
        email.take(1),
        telegram.take(1),
        password.take(1)
    ) { name, email, tg, password ->
        RegistrationParams(name, tg, email, password)
    }.flatMapConcat {
        registrationUseCase.execute(it)
    }
}

data class ValidationResults(
    val somethingEmpty: Boolean,
    val invalidEmail: Boolean,
    val invalidTelegram: Boolean,
    val badPassword: Boolean,
    val passwordsNotSame: Boolean,
)