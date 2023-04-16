package space.pixelsg.tutuor.main.lessons.list.adapter

import androidx.recyclerview.widget.DiffUtil
import org.joda.time.DateTime

data class LessonItem(
    val id: Long,
    val title: String,
    val description: String,
    val grade: Int,
    val date: DateTime,
    val studentName: String,
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<LessonItem>() {
            override fun areItemsTheSame(oldItem: LessonItem, newItem: LessonItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LessonItem, newItem: LessonItem) =
                oldItem == newItem
        }
    }
}


