package space.pixelsg.tutuor.auth.ui.sign_in

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.app.Router
import space.pixelsg.tutuor.auth.ui.activity.AuthActivity
import space.pixelsg.tutuor.auth.usecase.AuthResponse
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.onTextChanged
import space.pixelsg.tutuor.common.UiUtils.repeatOnCreated
import space.pixelsg.tutuor.common.UiUtils.top
import space.pixelsg.tutuor.databinding.SignInFragmentBinding
import javax.inject.Inject

internal class SignInFragment : BindingFragment<SignInFragmentBinding>({ inflater, container ->
    SignInFragmentBinding.inflate(inflater, container, false)
}) {

    @Inject
    lateinit var viewModel: SignInViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AuthActivity).component.signInSubcomponent.inject(this)

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.signInBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.signIn().collect {
                    when (it) {
                        is AuthResponse.Failed -> {
                            Snackbar.make(
                                binding.root,
                                it.t.localizedMessage ?: it.t.message ?: "Something went wrong",
                                Snackbar.LENGTH_SHORT
                            ).top().show()
                            it.t.printStackTrace()
                        }

                        is AuthResponse.Success -> {
                            Router.Main.launchSingleTop(requireContext())
                        }
                    }
                }
            }
        }
        repeatOnCreated {
            viewModel.isSignInAvailable.collect {
                binding.signInBtn.isEnabled = it
            }
        }
        repeatOnCreated {
            binding.telegramInput.onTextChanged().collect(viewModel.tg)
        }
        repeatOnCreated {
            binding.passwordInput.onTextChanged().collect(viewModel.password)
        }
    }
}