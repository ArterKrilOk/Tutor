package space.pixelsg.tutuor.main.lessons.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import space.pixelsg.tutuor.main.lessons.calendar.CalendarFragment
import space.pixelsg.tutuor.main.lessons.list.LessonsListFragment

class LessonsFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val listFragment by lazy { LessonsListFragment() }
    private val calendarFragment by lazy { CalendarFragment() }

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        1 -> listFragment
        else -> calendarFragment
    }
}