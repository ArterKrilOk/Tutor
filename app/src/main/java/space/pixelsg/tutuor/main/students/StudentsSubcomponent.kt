package space.pixelsg.tutuor.main.students

import dagger.Subcomponent

@Subcomponent
interface StudentsSubcomponent {
    fun inject(fragment: StudentsFragment)
}