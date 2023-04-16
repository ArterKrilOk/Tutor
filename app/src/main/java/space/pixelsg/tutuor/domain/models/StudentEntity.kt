package space.pixelsg.tutuor.domain.models

import androidx.recyclerview.widget.DiffUtil
import space.pixelsg.tutuor.mapper.Mappable

data class StudentEntity(
    override val id: Long,
    val fullName: String,
    val telegram: String,
    val teacherID: Long,
    val address: String,
    val telNumber: String
) : Entity<Long>, Mappable {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<StudentEntity>() {
            override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: StudentEntity,
                newItem: StudentEntity
            ) = oldItem == newItem
        }
    }
}
