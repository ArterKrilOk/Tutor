package space.pixelsg.tutuor.splash.ui.splash

import dagger.Subcomponent

@Subcomponent
interface SplashSubcomponent {
    fun inject(fragment: SplashFragment)
}