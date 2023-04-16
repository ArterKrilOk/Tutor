package space.pixelsg.tutuor.main.lessons.calendar.adapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import org.joda.time.LocalDate
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.adapter.CommonViewHolder
import space.pixelsg.tutuor.databinding.CalendarDayItemBinding
import space.pixelsg.tutuor.domain.models.LessonEntity

class DateViewHolder(
    binding: CalendarDayItemBinding,
    private val lessonClick: (LessonEntity) -> Unit
) :
    CommonViewHolder<CalendarItem.Date, CalendarDayItemBinding>(binding) {

    private val lessonPadding by lazy {
        context.resources.getDimensionPixelSize(R.dimen.calendar_border_width)
    }
    private val now by lazy { LocalDate() }

    override fun bindItem(model: CalendarItem.Date) {
        binding.textView.text = model.date.dayOfMonth.toString()
        binding.textViewActive.text = model.date.dayOfMonth.toString()
        val isCurrentDay = model.date == now

        binding.textViewActive.isVisible = isCurrentDay
        binding.textView.isVisible = !isCurrentDay

        binding.lessonsView.removeAllViews()
        model.lessons.forEach {
            binding.lessonsView.addView(
                TextView(context).apply {
                    text = it.title
                    background = AppCompatResources.getDrawable(context, R.drawable.lesson_bage)
                    textSize = 12f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setPadding(lessonPadding)
                    setOnClickListener { _ ->
                        lessonClick(it)
                    }
                }
            )
            binding.lessonsView.addView(
                TextView(context).apply {
                    setPadding(lessonPadding)
                    textSize = 0f
                }
            )
        }
    }
}