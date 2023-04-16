package space.pixelsg.tutuor.splash.ui.splash

import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.splash.usecase.IsAuthedUseCase
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    isAuthedUseCase: IsAuthedUseCase
) : CommonViewModel() {

    val authed = isAuthedUseCase.execute(Unit)
        .shareWhileSubscribed()
}