package space.pixelsg.tutuor.main.lessons.calendar.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.LocalDate
import org.joda.time.YearMonth

class CalendarPagingSource : PagingSource<Long, Calendar>() {
    override fun getRefreshKey(state: PagingState<Long, Calendar>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
            return null
//            anchorPage?.prevKey?.let {
//                getNextYearMonth(it.toYearMonth())?.toLong()
//            } ?: anchorPage?.nextKey?.let {
//                getPrevYearMonth(it.toYearMonth())?.toLong()
//            }
        }
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Calendar> {
        val currentDate = LocalDate()
        val yearMonth = params.key?.toYearMonth() ?: YearMonth()
        val monthOfYear = yearMonth.monthOfYear

        val items = withContext(Dispatchers.Unconfined) {
            val items = mutableListOf<Calendar>()
            val daysInMont = daysInMonth(currentDate.year, monthOfYear)

            for (i in 1..daysInMont) {
                val date = LocalDate(currentDate.year, monthOfYear, i)

                if (i == 1 && date.dayOfWeek > 1) {
                    for (j in 1 until date.dayOfWeek)
                        items.add(Calendar.EmptyCell(yearMonth))
                }

                items.add(Calendar.Day(date))

                if (i == daysInMont && date.dayOfWeek < 7) {
                    for (j in date.dayOfWeek + 1..7)
                        items.add(Calendar.EmptyCell(yearMonth))
                }
            }
            return@withContext items
        }

        return LoadResult.Page(
            data = items,
            prevKey = getPrevYearMonth(yearMonth)?.toLong(),
            nextKey = getNextYearMonth(yearMonth)?.toLong(),
        )
    }

    private fun getNextYearMonth(current: YearMonth): YearMonth? {
        return if (current.monthOfYear == 12) null //YearMonth(current.year + 1, 1)
        else if (current.monthOfYear + 1 in 1..12) YearMonth(current.year, current.monthOfYear + 1)
        else null
    }

    private fun getPrevYearMonth(current: YearMonth): YearMonth? {
        return if (current.monthOfYear == 1 && current.year - 1 >= 1970) null // YearMonth(current.year - 1, 12)
        else if (current.monthOfYear != 1) YearMonth(current.year, current.monthOfYear - 1)
        else null
    }

    private fun YearMonth.toLong(): Long =
        (year.toLong() shl 32) or (monthOfYear.toLong() and 0xFFFFFFFF)

    private fun Long.toYearMonth(): YearMonth =
        YearMonth((this shr 32).toInt(), (this and 0xFFFFFFFFL).toInt())

    private fun daysInMonth(year: Int, monthOfYear: Int): Int {
        val dateTime = LocalDate(year, monthOfYear, 1)
        return dateTime.dayOfMonth().maximumValue
    }
}


sealed class Calendar {
    data class EmptyCell(val yearMonth: YearMonth) : Calendar()
    data class Day(val localDate: LocalDate) : Calendar()
}