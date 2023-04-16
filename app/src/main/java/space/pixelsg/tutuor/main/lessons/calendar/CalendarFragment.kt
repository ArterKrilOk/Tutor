package space.pixelsg.tutuor.main.lessons.calendar

import android.os.Bundle
import android.view.View
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.databinding.CalendarFragmentBinding

class CalendarFragment : BindingFragment<CalendarFragmentBinding>({ inflater, container ->
    CalendarFragmentBinding.inflate(inflater, container, false)
}) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}