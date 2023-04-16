package space.pixelsg.tutuor.main.lessons.list.adapter

import androidx.core.view.isVisible
import space.pixelsg.tutuor.common.adapter.CommonViewHolder
import space.pixelsg.tutuor.databinding.LessonItemBinding

class LessonViewHolder(
    binding: LessonItemBinding
) : CommonViewHolder<LessonItem, LessonItemBinding>(binding) {
    override fun bindItem(model: LessonItem) {
        binding.gradeView.isVisible = model.grade > 0
        binding.gradeView.text = model.grade.toString()

        binding.titleView.text = model.title
        binding.studentView.text = model.studentName
        binding.descriptionView.text = model.description
        binding.dateView.text = model.date.toLocalDate().toString("dd.MM.yyyy")
    }
}