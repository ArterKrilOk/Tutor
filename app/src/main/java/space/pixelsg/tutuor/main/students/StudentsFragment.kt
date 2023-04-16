package space.pixelsg.tutuor.main.students

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.collectOnOnLifecycle
import space.pixelsg.tutuor.databinding.StudentsFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.students.adapter.StudentsAdapter
import javax.inject.Inject

class StudentsFragment : BindingFragment<StudentsFragmentBinding>({ inflater, container ->
    StudentsFragmentBinding.inflate(inflater, container, false)
}) {
    @Inject
    lateinit var viewModel: StudentsViewModel
    private val studentAdapter by lazy {
        StudentsAdapter(
            onItemClick = {
                findNavController().navigate(
                    R.id.action_actionStudents_to_studentInfoFragment,
                    bundleOf("id" to it.id)
                )
            },
            onSendClick = {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("tg://resolve?domain=${it.telegram}")
                    )
                )
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.studentsSubcomponent.inject(this)

        binding.recyclerView.apply {
            adapter = studentAdapter
        }

        viewModel.students.collectOnOnLifecycle(this) {
            studentAdapter.items = it
            binding.noItemsView.isVisible = it.isEmpty()
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_actionStudents_to_createStudentBottomFragment)
        }
    }
}