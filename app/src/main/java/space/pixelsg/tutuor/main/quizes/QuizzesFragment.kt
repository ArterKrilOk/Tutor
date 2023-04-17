package space.pixelsg.tutuor.main.quizes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.collectOnOnLifecycle
import space.pixelsg.tutuor.databinding.QuizzesFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.quizes.adapter.QuizAdapter
import javax.inject.Inject

class QuizzesFragment : BindingFragment<QuizzesFragmentBinding>({ inflater, container ->
    QuizzesFragmentBinding.inflate(inflater, container, false)
}) {
    private val quizzesAdapter by lazy {
        QuizAdapter(
            onOpenClick = {
                startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(it.url)
                    }
                )
            }
        )
    }

    @Inject
    lateinit var viewModel: QuizzesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.quizzesSubcomponent.inject(this)

        binding.recyclerView.adapter = quizzesAdapter

        viewModel.items.collectOnOnLifecycle(this) {
            quizzesAdapter.items = it
            binding.noItemsView.isVisible = it.isEmpty()
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_actionQuizzes_to_createQuizBottomFragment)
        }
    }
}