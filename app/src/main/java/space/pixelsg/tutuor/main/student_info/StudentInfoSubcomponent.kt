package space.pixelsg.tutuor.main.student_info

import dagger.Subcomponent

@Subcomponent
interface StudentInfoSubcomponent {
    fun inject(fragment: StudentInfoFragment)
}