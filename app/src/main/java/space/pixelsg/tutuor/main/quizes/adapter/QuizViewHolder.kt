package space.pixelsg.tutuor.main.quizes.adapter

import space.pixelsg.tutuor.common.adapter.CommonViewHolder
import space.pixelsg.tutuor.databinding.QuizItemBinding
import space.pixelsg.tutuor.domain.models.QuizEntity

class QuizViewHolder(
    binding: QuizItemBinding,
    private val onOpenClick: (QuizEntity) -> Unit
) : CommonViewHolder<QuizEntity, QuizItemBinding>(
    binding
) {
    override fun bindItem(model: QuizEntity) {
        binding.titleView.text = model.title
        binding.urlView.text = model.url
        binding.button.setOnClickListener {
            onOpenClick(model)
        }
    }
}