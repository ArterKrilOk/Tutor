package space.pixelsg.tutuor.main.lessons.calendar.adapter

import space.pixelsg.tutuor.common.adapter.CommonViewHolder
import space.pixelsg.tutuor.databinding.CalendarSpaceItemBinding

class SpaceViewHolder(binding: CalendarSpaceItemBinding) :
    CommonViewHolder<CalendarItem.Space, CalendarSpaceItemBinding>(
        binding
    ) {
    override fun bindItem(model: CalendarItem.Space) = Unit
}