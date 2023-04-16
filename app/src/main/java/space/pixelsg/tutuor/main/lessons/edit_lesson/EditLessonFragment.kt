package space.pixelsg.tutuor.main.lessons.edit_lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.UiUtils.collectOnOnLifecycle
import space.pixelsg.tutuor.common.UiUtils.onTextChanged
import space.pixelsg.tutuor.common.UiUtils.replaceText
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.EditLessonBottomFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.lessons.usecase.EditLessonResult
import javax.inject.Inject

class EditLessonFragment : BottomSheetDialogFragment() {
    private lateinit var binding: EditLessonBottomFragmentBinding

    @Inject
    lateinit var viewModel: EditLessonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditLessonBottomFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.lessonsSubcomponent
            .editLessonSubcomponent.inject(this)

        val lessonID = requireArguments().getLong("id", -1)
        if (lessonID == -1L) {
            findNavController().popBackStack()
            return
        }
        viewModel.loadLesson(lessonID)

        viewModel.canEdit.collectOnOnLifecycle(this) {
            binding.descriptionInputLayout.isEnabled = it
            binding.gradeView.isEnabled = it
            binding.toolbar.menu.findItem(R.id.actionEdit).isVisible = !it
            binding.toolbar.menu.findItem(R.id.actionSave).isVisible = it
            binding.chipNone.isEnabled = it
            binding.chip1.isEnabled = it
            binding.chip2.isEnabled = it
            binding.chip3.isEnabled = it
            binding.chip4.isEnabled = it
            binding.chip5.isEnabled = it
        }

        binding.toolbar.menu.findItem(R.id.actionEdit).setOnMenuItemClickListener {
            viewModel.edit()
            true
        }
        binding.toolbar.menu.findItem(R.id.actionSave).setOnMenuItemClickListener {
            viewModel.save().collectOnOnLifecycle(this) {
                when (it) {
                    is EditLessonResult.Failed -> {
                        it.t.printStackTrace()
                        Snackbar.make(
                            binding.root,
                            it.t.localizedMessage ?: it.t.message ?: "Something went wrong",
                            Snackbar.LENGTH_SHORT
                        ).top().show()
                    }

                    is EditLessonResult.Success ->
                        findNavController().popBackStack()
                }
            }
            true
        }

        viewModel.lesson.collectOnOnLifecycle(this) {
            binding.toolbar.title = it.title
            binding.studentView.text =
                getString(R.string.specific_student, "")
            binding.dateView.text =
                getString(R.string.specific_date, it.date.toString("dd.MM.yyyy HH:mm"))
        }
        viewModel.isChanged.collectOnOnLifecycle(this) {
            binding.toolbar.menu.findItem(R.id.actionSave).isEnabled = it
        }
        viewModel.description.collectOnOnLifecycle(this) {
            binding.descriptionInput.replaceText(it)
        }
        binding.chipNone.setOnClickListener {
            binding.gradeView.clearCheck()
            viewModel.setGrade(-1)
        }
        binding.chip1.setOnClickListener {
            binding.gradeView.clearCheck()
            viewModel.setGrade(1)
        }
        binding.chip2.setOnClickListener {
            binding.gradeView.clearCheck()
            viewModel.setGrade(2)
        }
        binding.chip3.setOnClickListener {
            binding.gradeView.clearCheck()
            viewModel.setGrade(3)
        }
        binding.chip4.setOnClickListener {
            binding.gradeView.clearCheck()
            viewModel.setGrade(4)
        }
        binding.chip5.setOnClickListener {
            binding.gradeView.clearCheck()
            viewModel.setGrade(5)
        }
        binding.descriptionInput.onTextChanged()
            .collectOnOnLifecycle(this, Lifecycle.State.CREATED, viewModel.description)

        viewModel.grade.distinctUntilChanged { old, new -> old == new }
            .collectOnOnLifecycle(this) {
                binding.gradeView.clearCheck()
                when (it) {
                    1 -> binding.chip1.isChecked = true
                    2 -> binding.chip2.isChecked = true
                    3 -> binding.chip3.isChecked = true
                    4 -> binding.chip4.isChecked = true
                    5 -> binding.chip5.isChecked = true
                    else -> binding.chipNone.isChecked = true
                }
            }
    }
}