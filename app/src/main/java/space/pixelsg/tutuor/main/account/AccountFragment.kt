package space.pixelsg.tutuor.main.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.app.Router
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.collectIn
import space.pixelsg.tutuor.common.UiUtils.collectOnOnLifecycle
import space.pixelsg.tutuor.common.UiUtils.replaceText
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.AccountFragmentBinding
import space.pixelsg.tutuor.main.activity.MainActivity
import javax.inject.Inject

class AccountFragment : BindingFragment<AccountFragmentBinding>({ inflater, container ->
    AccountFragmentBinding.inflate(inflater, container, false)
}) {
    @Inject
    lateinit var viewModel: AccountViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).component.accountSubcomponent.inject(this)

        binding.toolbar.menu.findItem(R.id.actionEdit).isVisible = false
        binding.toolbar.menu.findItem(R.id.actionSave).isVisible = false
        binding.fullNameInputLayout.isEnabled = false
        binding.emailInputLayout.isEnabled = false
        binding.telegramInputLayout.isEnabled = false

        viewModel.teacherInfo.collectOnOnLifecycle(this) {
            binding.studentsCountView.text =
                getString(R.string.account_students_count, it.studentsCount)
            binding.fullNameInput.replaceText(it.fullName)
            binding.emailInput.replaceText(it.email)
            binding.telegramInput.replaceText(it.telegram)
        }

        viewModel.error.collectOnOnLifecycle(this) {
            Snackbar
                .make(binding.root, it, Snackbar.LENGTH_SHORT)
                .top()
                .show()
        }

        binding.logOutBtn.setOnClickListener {
            viewModel.logOut().collectIn(lifecycleScope) {
                Router.Splash.launchSingleTop(requireContext())
            }
        }
    }
}