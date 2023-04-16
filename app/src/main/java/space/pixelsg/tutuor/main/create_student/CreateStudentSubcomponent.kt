package space.pixelsg.tutuor.main.create_student

import dagger.Subcomponent

@Subcomponent
interface CreateStudentSubcomponent {
    fun inject(fragment: CreateStudentBottomFragment)
}