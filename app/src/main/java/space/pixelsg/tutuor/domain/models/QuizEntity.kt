package space.pixelsg.tutuor.domain.models

import androidx.recyclerview.widget.DiffUtil
import org.joda.time.DateTime
import space.pixelsg.tutuor.mapper.Mappable

data class QuizEntity(
    override val id: Long,
    val title: String,
    val url: String,
    val dateTime: DateTime,
) : Entity<Long>, Mappable {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<QuizEntity>() {
            override fun areItemsTheSame(oldItem: QuizEntity, newItem: QuizEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: QuizEntity, newItem: QuizEntity) =
                oldItem == newItem

        }
    }
}
