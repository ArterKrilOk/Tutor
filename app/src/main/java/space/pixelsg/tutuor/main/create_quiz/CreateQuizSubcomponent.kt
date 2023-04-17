package space.pixelsg.tutuor.main.create_quiz

import dagger.Subcomponent

@Subcomponent
interface CreateQuizSubcomponent {
    fun inject(fragment: CreateQuizBottomFragment)
}