package space.pixelsg.tutuor.main.lessons.calendar

import dagger.Subcomponent

@Subcomponent
interface CalendarSubcomponent {
    fun inject(fragment: CalendarFragment)
}