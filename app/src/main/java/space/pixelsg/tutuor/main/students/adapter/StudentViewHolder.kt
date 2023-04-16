package space.pixelsg.tutuor.main.students.adapter

import android.graphics.Color
import space.pixelsg.tutuor.common.adapter.CommonViewHolder
import space.pixelsg.tutuor.databinding.StudentItemBinding
import space.pixelsg.tutuor.domain.models.StudentEntity
import java.util.Locale

class StudentViewHolder(
    binding: StudentItemBinding,
    private val onSendClick: (StudentEntity) -> Unit
) : CommonViewHolder<StudentEntity, StudentItemBinding>(binding) {
    override fun bindItem(model: StudentEntity) {
        binding.fullNameView.text = model.fullName
        binding.addressView.text = model.address

        val firstLetter = model.fullName.first().toString().uppercase(Locale.getDefault())
        binding.avatarText.text = firstLetter
        binding.avatarCard.setCardBackgroundColor(Color.parseColor(stringToHexColor(firstLetter)))
        binding.button.setOnClickListener { onSendClick(model) }
    }

    private fun stringToHexColor(str: String): String {
        val hex = Integer.toHexString(str.hashCode()).leading(6, '0')
        return "#${hex.substring(0, 6)}"
    }

    private fun String.leading(reqSize: Int, char: Char): String {
        var result = "" + this
        while (result.length < reqSize) result = char + result
        return result
    }
}