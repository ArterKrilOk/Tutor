package space.pixelsg.tutuor.main.students.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import space.pixelsg.tutuor.common.adapter.CommonAdapter
import space.pixelsg.tutuor.databinding.StudentItemBinding
import space.pixelsg.tutuor.domain.models.StudentEntity

class StudentsAdapter(
    onItemClick: (StudentEntity) -> Unit, private val onSendClick: (StudentEntity) -> Unit
) : CommonAdapter<StudentEntity, StudentViewHolder>(StudentEntity.DIFF_UTIL, onItemClick) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        StudentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onSendClick
    )
}