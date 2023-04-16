package space.pixelsg.tutuor.main.lessons.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import space.pixelsg.tutuor.common.adapter.CommonAdapter
import space.pixelsg.tutuor.databinding.LessonItemBinding

class LessonViewAdapter(
    onLessonClick: (LessonItem) -> Unit
) : CommonAdapter<LessonItem, LessonViewHolder>(LessonItem.DIFF_UTIL, onLessonClick) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LessonViewHolder(
        LessonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}