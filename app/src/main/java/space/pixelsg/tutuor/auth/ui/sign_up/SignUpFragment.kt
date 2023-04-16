package space.pixelsg.tutuor.auth.ui.sign_up

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.app.Router
import space.pixelsg.tutuor.auth.ui.activity.AuthActivity
import space.pixelsg.tutuor.auth.usecase.RegistrationResponse
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.onTextChanged
import space.pixelsg.tutuor.common.UiUtils.repeatOnCreated
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.SignUpFragmentBinding
import javax.inject.Inject

internal class SignUpFragment : BindingFragment<SignUpFragmentBinding>({ inflater, container ->
    SignUpFragmentBinding.inflate(inflater, container, false)
}) {

    @Inject
    lateinit var viewModel: SignUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AuthActivity).component.signUpSubcomponent.inject(this)

        binding.signInBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        binding.signUpBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.signUp().collect {
                    when (it) {
                        is RegistrationResponse.Failed -> {
                            Snackbar.make(
                                binding.root,
                                it.t.localizedMessage ?: it.t.message ?: "Something went wrong",
                                Snackbar.LENGTH_SHORT
                            ).top().show()
                            it.t.printStackTrace()
                        }

                        is RegistrationResponse.Success -> {
                            Router.Main.launchSingleTop(requireContext())
                        }
                    }
                }
            }
        }
        repeatOnCreated {
            viewModel.successValidation.collect {
                binding.signUpBtn.isEnabled = it
            }
        }
        repeatOnCreated {
            binding.fullNameInput.onTextChanged().collect(viewModel.name)
        }
        repeatOnCreated {
            binding.emailInput.onTextChanged().collect(viewModel.email)
        }
        repeatOnCreated {
            binding.telegramInput.onTextChanged().collect(viewModel.telegram)
        }
        repeatOnCreated {
            binding.passwordInput.onTextChanged().collect(viewModel.password)
        }
        repeatOnCreated {
            binding.repeatPasswordInput.onTextChanged().collect(viewModel.rePassword)
        }
        repeatOnCreated {
            viewModel.validation.collect {
                val results = mutableListOf<String>()
                if (it.somethingEmpty) results.add(getString(R.string.something_is_empty))
                if (it.invalidEmail) results.add(getString(R.string.invalid_email))
                if (it.invalidTelegram) results.add(getString(R.string.invalid_tg))
                if (it.badPassword) results.add(getString(R.string.weak_password))
                if (it.passwordsNotSame) results.add(getString(R.string.passwords_not_same))
                binding.errorView.text = results.joinToString("\n")
            }
        }
    }
}