package space.pixelsg.tutuor.auth.ui.sign_in

import dagger.Subcomponent

@Subcomponent
internal interface SignInSubcomponent {
    fun inject(fragment: SignInFragment)
}