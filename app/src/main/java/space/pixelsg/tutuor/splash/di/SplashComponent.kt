package space.pixelsg.tutuor.splash.di

import dagger.Component
import space.pixelsg.tutuor.data.di.DataComponent
import space.pixelsg.tutuor.splash.ui.splash.SplashSubcomponent

@SplashScope
@Component(
    dependencies = [DataComponent::class],
)
internal interface SplashComponent {
    val splashSubcomponent: SplashSubcomponent
}