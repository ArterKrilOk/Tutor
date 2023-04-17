package space.pixelsg.tutuor.main.quizes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import space.pixelsg.tutuor.common.adapter.CommonAdapter
import space.pixelsg.tutuor.databinding.QuizItemBinding
import space.pixelsg.tutuor.domain.models.QuizEntity

class QuizAdapter(
    private val onOpenClick: (QuizEntity) -> Unit
) : CommonAdapter<QuizEntity, QuizViewHolder>(QuizEntity.DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = QuizViewHolder(
        QuizItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onOpenClick
    )
}