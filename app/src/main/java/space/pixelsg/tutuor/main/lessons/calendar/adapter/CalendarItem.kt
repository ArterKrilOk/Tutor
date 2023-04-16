package space.pixelsg.tutuor.main.lessons.calendar.adapter

import androidx.recyclerview.widget.DiffUtil
import org.joda.time.LocalDate
import org.joda.time.YearMonth
import space.pixelsg.tutuor.domain.models.LessonEntity

sealed class CalendarItem {
    data class Date(
        val date: LocalDate,
        override val yearMonth: YearMonth = YearMonth(date.year, date.monthOfYear),
        val lessons: List<LessonEntity> = emptyList(),
    ) : CalendarItem()

    data class MonthSeparator(
        override val yearMonth: YearMonth,
    ) : CalendarItem()

    data class Space(
        override val yearMonth: YearMonth,
        val size: Int = 1,
    ) : CalendarItem()

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<CalendarItem>() {
            override fun areItemsTheSame(oldItem: CalendarItem, newItem: CalendarItem) = when {
                oldItem is Date && newItem is Date ->
                    oldItem.date == newItem.date

                oldItem is MonthSeparator && newItem is MonthSeparator ->
                    oldItem.yearMonth == newItem.yearMonth

                oldItem is Space && newItem is Space ->
                    oldItem.size == newItem.size

                else -> false
            }

            override fun areContentsTheSame(oldItem: CalendarItem, newItem: CalendarItem) =
                oldItem == newItem
        }
    }

    abstract val yearMonth: YearMonth
}