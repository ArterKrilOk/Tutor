package space.pixelsg.tutuor.main.lessons.list

import dagger.Subcomponent

@Subcomponent
interface LessonsListSubcomponent {
    fun inject(fragment: LessonsListFragment)
}