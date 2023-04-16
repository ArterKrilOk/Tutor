package space.pixelsg.tutuor.main.lessons.calendar

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.repeatOnCreated
import space.pixelsg.tutuor.databinding.CalendarFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.lessons.calendar.adapter.CalendarAdapter
import space.pixelsg.tutuor.main.lessons.calendar.adapter.CalendarItem
import javax.inject.Inject

class CalendarFragment : BindingFragment<CalendarFragmentBinding>({ inflater, container ->
    CalendarFragmentBinding.inflate(inflater, container, false)
}) {

    private val calendarAdapter by lazy {
        CalendarAdapter(
            onDateItemClick = {

            },
            onLessonClick = {
                findNavController().navigate(
                    R.id.action_actionLessons_to_editLessonFragment,
                    bundleOf("id" to it.id)
                )
            }
        )
    }

    @Inject
    lateinit var viewModel: CalendarViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.lessonsSubcomponent
            .calendarSubcomponent.inject(this)

        binding.recyclerView.apply {
            adapter = calendarAdapter
            setHasFixedSize(false)
            layoutManager = GridLayoutManager(requireContext(), 7).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val (item, type) = calendarAdapter.getItemAndType(position)
                        return when (type) {
                            CalendarAdapter.MONTH_ITEM -> 7
                            CalendarAdapter.SPACE_ITEM -> (item as CalendarItem.Space).size
                            else -> 1
                        }
                    }
                }
            }
        }

        repeatOnCreated {
            viewModel.items.collectLatest {
                calendarAdapter.submitData(it)
            }
        }
    }
}