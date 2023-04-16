package space.pixelsg.tutuor.main.lessons.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.collectOnOnLifecycle
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.LessonsListFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.lessons.list.adapter.LessonViewAdapter
import javax.inject.Inject

class LessonsListFragment : BindingFragment<LessonsListFragmentBinding>({ inflater, container ->
    LessonsListFragmentBinding.inflate(inflater, container, false)
}) {
    private val lessonsAdapter by lazy {
        LessonViewAdapter(
            onLessonClick = {

            }
        )
    }

    @Inject
    lateinit var viewModel: LessonsListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.lessonsSubcomponent
            .lessonsListSubcomponent.inject(this)

        binding.recyclerView.adapter = lessonsAdapter

        viewModel.error.collectOnOnLifecycle(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).top().show()
        }
        viewModel.lessons.collectOnOnLifecycle(this) {
            lessonsAdapter.items = it
            binding.noItemsView.isVisible = it.isEmpty()
        }
    }
}