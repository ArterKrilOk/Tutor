package space.pixelsg.tutuor.main.lessons.create_lesson

import dagger.Subcomponent

@Subcomponent
interface CreateLessonSubcomponent {
    fun inject(fragment: CreateLessonBottomFragment)
}