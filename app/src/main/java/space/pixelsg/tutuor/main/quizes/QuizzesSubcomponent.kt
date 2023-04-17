package space.pixelsg.tutuor.main.quizes

import dagger.Subcomponent

@Subcomponent
interface QuizzesSubcomponent {
    fun inject(fragment: QuizzesFragment)
}