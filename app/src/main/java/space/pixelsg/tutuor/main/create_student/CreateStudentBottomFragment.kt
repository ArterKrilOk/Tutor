package space.pixelsg.tutuor.main.create_student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.UiUtils.collectIn
import space.pixelsg.tutuor.common.UiUtils.onTextChanged
import space.pixelsg.tutuor.common.UiUtils.repeatOnCreated
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.CreateStudentBottomFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.usecase.CreateStudentResult
import javax.inject.Inject

class CreateStudentBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: CreateStudentBottomFragmentBinding

    @Inject
    lateinit var viewModel: CreateStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CreateStudentBottomFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.createStudentSubcomponent.inject(this)

        repeatOnCreated {
            binding.fullNameInput.onTextChanged().collect(viewModel.fullName)
        }
        repeatOnCreated {
            binding.telegramInput.onTextChanged().collect(viewModel.tg)
        }
        repeatOnCreated {
            binding.telInput.onTextChanged().collect(viewModel.tel)
        }
        repeatOnCreated {
            binding.addressInput.onTextChanged().collect(viewModel.address)
        }
        repeatOnCreated {
            viewModel.isAddEnabled.collect {
                binding.toolbar.menu.findItem(R.id.actionCreate).isEnabled = it
            }
        }
        binding.toolbar.menu.findItem(R.id.actionCreate).setOnMenuItemClickListener {
            viewModel.crateUser().collectIn(lifecycleScope) {
                when (it) {
                    is CreateStudentResult.Failed -> {
                        it.t.printStackTrace()
                        Snackbar.make(
                            binding.root,
                            it.t.localizedMessage ?: it.t.message ?: "Something went wrong",
                            Snackbar.LENGTH_SHORT
                        ).top().show()
                    }

                    is CreateStudentResult.Success -> {
                        findNavController().popBackStack()
                    }
                }
            }
            true
        }
    }
}