package space.pixelsg.tutuor.main.student_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.UiUtils.repeatOnCreated
import space.pixelsg.tutuor.common.UiUtils.replaceText
import space.pixelsg.tutuor.databinding.CreateStudentBottomFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import javax.inject.Inject

class StudentInfoFragment : BottomSheetDialogFragment() {
    private lateinit var binding: CreateStudentBottomFragmentBinding
    @Inject
    lateinit var viewModel: StudentInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CreateStudentBottomFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fullNameInputLayout.isEnabled = false
        binding.addressInputLayout.isEnabled = false
        binding.telInputLayout.isEnabled = false
        binding.telegramInputLayout.isEnabled = false
        binding.toolbar.menu.findItem(R.id.actionCreate).isVisible = false
        binding.toolbar.title = getString(R.string.student)

        (activity as MainActivity).component.studentInfoSubcomponent.inject(this)

        val studentID = requireArguments().getLong("id", -1)
        if (studentID == -1L) findNavController().popBackStack()
        else {
            repeatOnCreated {
                viewModel.error.collect {
                    findNavController().popBackStack()
                }
            }
            repeatOnCreated {
                viewModel.getStudent(studentID).collect {
                    binding.fullNameInput.replaceText(it.fullName)
                    binding.addressInput.replaceText(it.address)
                    binding.telInput.replaceText(it.telNumber)
                    binding.telegramInput.replaceText(it.telegram)
                }
            }
        }


    }
}