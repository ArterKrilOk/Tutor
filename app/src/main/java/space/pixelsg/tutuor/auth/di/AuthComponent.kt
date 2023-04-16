package space.pixelsg.tutuor.auth.di

import dagger.Component
import space.pixelsg.tutuor.auth.ui.sign_in.SignInSubcomponent
import space.pixelsg.tutuor.auth.ui.sign_up.SignUpSubcomponent
import space.pixelsg.tutuor.auth.usecase.AuthUseCase
import space.pixelsg.tutuor.auth.usecase.RegistrationUseCase
import space.pixelsg.tutuor.data.di.DataComponent

@AuthScope
@Component(
    dependencies = [DataComponent::class],
)
internal interface AuthComponent {
    val authUseCase: AuthUseCase
    val registrationUseCase: RegistrationUseCase

    val signInSubcomponent: SignInSubcomponent
    val signUpSubcomponent: SignUpSubcomponent
}