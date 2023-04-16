package space.pixelsg.tutuor.main.lessons.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import space.pixelsg.tutuor.databinding.CalendarDayItemBinding
import space.pixelsg.tutuor.databinding.CalendarSpaceItemBinding
import space.pixelsg.tutuor.databinding.SeparatorItemBinding
import space.pixelsg.tutuor.domain.models.LessonEntity

class CalendarAdapter(
    private val onDateItemClick: (CalendarItem.Date) -> Unit,
    private val onLessonClick: (LessonEntity) -> Unit
) : PagingDataAdapter<CalendarItem, RecyclerView.ViewHolder>(CalendarItem.DIFF_UTIL) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateViewHolder -> holder.bindNullable(
                getItem(position) as CalendarItem.Date, onDateItemClick
            )

            is MonthSeparatorViewHolder -> holder.bindNullable(getItem(position) as CalendarItem.MonthSeparator)

            is SpaceViewHolder -> holder.bind(getItem(position) as CalendarItem.Space)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            DAY_ITEM -> DateViewHolder(
                CalendarDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onLessonClick
            )

            MONTH_ITEM -> MonthSeparatorViewHolder(
                SeparatorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> SpaceViewHolder(
                CalendarSpaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is CalendarItem.Date -> DAY_ITEM
        is CalendarItem.MonthSeparator -> MONTH_ITEM
        is CalendarItem.Space -> SPACE_ITEM
        else -> 0
    }

    fun getItemAndType(position: Int): Pair<CalendarItem?, Int> {
        return getItem(position) to getItemViewType(position)
    }

    companion object {
        const val DAY_ITEM = 1
        const val MONTH_ITEM = 2
        const val SPACE_ITEM = 3
    }
}