package space.pixelsg.tutuor.main.lessons.calendar.adapter

import android.annotation.SuppressLint
import org.joda.time.YearMonth
import space.pixelsg.tutuor.common.adapter.CommonViewHolder
import space.pixelsg.tutuor.databinding.SeparatorItemBinding
import java.util.Locale

class MonthSeparatorViewHolder(binding: SeparatorItemBinding) :
    CommonViewHolder<CalendarItem.MonthSeparator, SeparatorItemBinding>(binding) {
    @SuppressLint("SetTextI18n")
    override fun bindItem(model: CalendarItem.MonthSeparator) {
        binding.textView.text = model.yearMonth.monthOfYear().getAsText(Locale.getDefault())
        if (model.yearMonth.year != YearMonth().year)
            binding.textView.text = "${
                model.yearMonth.monthOfYear().getAsText(Locale.getDefault())
            } ${model.yearMonth.year}"
    }
}