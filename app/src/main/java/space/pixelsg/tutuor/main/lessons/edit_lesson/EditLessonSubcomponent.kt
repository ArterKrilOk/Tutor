package space.pixelsg.tutuor.main.lessons.edit_lesson

import dagger.Subcomponent

@Subcomponent
sealed interface EditLessonSubcomponent {
    fun inject(fragment: EditLessonFragment)
}