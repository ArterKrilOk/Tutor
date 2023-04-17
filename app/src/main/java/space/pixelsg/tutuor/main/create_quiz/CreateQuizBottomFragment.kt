package space.pixelsg.tutuor.main.create_quiz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.UiUtils.collectIn
import space.pixelsg.tutuor.common.UiUtils.collectOnOnLifecycle
import space.pixelsg.tutuor.common.UiUtils.onTextChanged
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.CreateQuizBottomFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.usecase.CreateQuizResult
import javax.inject.Inject

class CreateQuizBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: CreateQuizBottomFragmentBinding

    @Inject
    lateinit var viewModel: CreateQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CreateQuizBottomFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.createQuizSubcomponent.inject(this)

        binding.titleInput.onTextChanged()
            .collectOnOnLifecycle(this, Lifecycle.State.CREATED, viewModel.title)

        binding.urlInput.onTextChanged()
            .collectOnOnLifecycle(this, Lifecycle.State.CREATED, viewModel.url)

        binding.btnCreateForm.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://docs.google.com/forms/")
                }
            )
        }

        viewModel.isAddEnabled.collectOnOnLifecycle(this) {
            binding.toolbar.menu.findItem(R.id.actionCreate).isEnabled = it
        }
        binding.toolbar.menu.findItem(R.id.actionCreate).setOnMenuItemClickListener {
            viewModel.createQuiz().collectIn(lifecycleScope) {
                when (it) {
                    is CreateQuizResult.Failed -> {
                        it.t.printStackTrace()
                        Snackbar.make(
                            binding.root,
                            it.t.localizedMessage ?: it.t.message ?: "Something went wrong",
                            Snackbar.LENGTH_SHORT
                        ).top().show()
                    }

                    is CreateQuizResult.Success -> {
                        findNavController().popBackStack()
                    }
                }
            }
            true
        }
    }
}