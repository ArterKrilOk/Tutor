package space.pixelsg.tutuor.auth.ui.sign_up

import dagger.Subcomponent

@Subcomponent
internal interface SignUpSubcomponent {
    fun inject(fragment: SignUpFragment)
}