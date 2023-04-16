package space.pixelsg.tutuor.main.lessons.create_lesson

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.common.UiUtils.collectOnOnLifecycle
import space.pixelsg.tutuor.common.UiUtils.onTextChanged
import space.pixelsg.tutuor.common.UiUtils.replaceText
import space.pixelsg.tutuor.common.UiUtils.setupSimple
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.CreateLessonBottomFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.main.lessons.usecase.CreateLessonResult
import java.util.Calendar
import javax.inject.Inject

class CreateLessonBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: CreateLessonBottomFragmentBinding

    @Inject
    lateinit var viewModel: CreateLessonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateLessonBottomFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.lessonsSubcomponent
            .createLessonsSubcomponent.inject(this)

        viewModel.students.collectOnOnLifecycle(this) {
            binding.spinner.setupSimple(it, converter = { student -> student.fullName })
                .collect(viewModel.student)
        }
        viewModel.canCreate.collectOnOnLifecycle(this) {
            binding.toolbar.menu.findItem(R.id.actionCreate).isEnabled = it
        }

        viewModel.datetime.collectOnOnLifecycle(this) {
            binding.dateInput.replaceText(it)
        }

        binding.buttonSelectDatetime.setOnClickListener {
            openDateTimePicker()
        }

        binding.titleInput.onTextChanged()
            .collectOnOnLifecycle(this, Lifecycle.State.CREATED, viewModel.title)
        binding.descriptionInput.onTextChanged()
            .collectOnOnLifecycle(this, Lifecycle.State.CREATED, viewModel.description)

        binding.toolbar.menu.findItem(R.id.actionCreate).setOnMenuItemClickListener {
            viewModel.create().collectOnOnLifecycle(this) {
                when (it) {
                    is CreateLessonResult.Failed -> {
                        it.t.printStackTrace()
                        Snackbar.make(
                            binding.root,
                            it.t.localizedMessage ?: it.t.message ?: "Something went wrong",
                            Snackbar.LENGTH_SHORT
                        ).top().show()
                    }

                    is CreateLessonResult.Success ->
                        findNavController().popBackStack()
                }
            }
            true
        }
    }

    private fun openDateTimePicker() {
        DatePickerDialog { y, m, d ->
            lifecycleScope.launch {
                viewModel.date.emit("${d}.${m + 1}.$y")
            }
        }.show(childFragmentManager, "datepicker")
        TimePickerDialog { h, m ->
            lifecycleScope.launch {
                viewModel.time.emit("${h}:$m")
            }
        }.show(childFragmentManager, "timepicker")
    }

    class DatePickerDialog(
        private val callback: (y: Int, m: Int, d: Int) -> Unit
    ) : DialogFragment(),
        android.app.DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()

            return android.app.DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            callback(year, month, dayOfMonth)
        }
    }

    class TimePickerDialog(
        private val callback: (h: Int, m: Int) -> Unit
    ) : DialogFragment(),
        android.app.TimePickerDialog.OnTimeSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()

            return android.app.TimePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
        }

        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
            callback(hourOfDay, minute)
        }
    }
}

