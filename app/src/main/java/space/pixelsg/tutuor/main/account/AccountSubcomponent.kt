package space.pixelsg.tutuor.main.account

import dagger.Subcomponent

@Subcomponent
interface AccountSubcomponent {
    fun inject(fragment: AccountFragment)
}