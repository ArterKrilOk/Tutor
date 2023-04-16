package space.pixelsg.tutuor.main.lessons.calendar

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.lessons.calendar.adapter.CalendarItem
import space.pixelsg.tutuor.main.lessons.calendar.datasource.Calendar
import space.pixelsg.tutuor.main.lessons.calendar.datasource.CalendarPagingSource
import space.pixelsg.tutuor.main.lessons.usecase.GetLessonsUseCase
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    getLessonsUseCase: GetLessonsUseCase
) : CommonViewModel() {
    private val pagingSource by lazy { CalendarPagingSource() }
    private val lessons = getLessonsUseCase.execute(Unit)
        .catch { error.emit(it.localizedMessage ?: it.message ?: "Something vent wrong") }
        .shareWhileSubscribed()

    val items = combine(
        Pager(
            PagingConfig(
                30,
                initialLoadSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 10
            )
        ) {
            pagingSource
        }.flow.cachedIn(viewModelScope),
        lessons
    ) { pagingData, lessons ->
        pagingData.map {
            when (it) {
                is Calendar.Day -> CalendarItem.Date(
                    date = it.localDate,
                    lessons = lessons.filter { l -> l.date.toLocalDate() == it.localDate }
                )

                is Calendar.EmptyCell -> CalendarItem.Space(it.yearMonth, 1)
            }
        }.insertSeparators { prevItem: CalendarItem?, nextItem: CalendarItem? ->
            when {
                prevItem != null && nextItem != null && prevItem.yearMonth.monthOfYear != nextItem.yearMonth.monthOfYear ->
                    CalendarItem.MonthSeparator(nextItem.yearMonth)

                prevItem == null && nextItem != null ->
                    CalendarItem.MonthSeparator(nextItem.yearMonth)

                else -> null
            }
        }
    }.shareWhileSubscribed()
}