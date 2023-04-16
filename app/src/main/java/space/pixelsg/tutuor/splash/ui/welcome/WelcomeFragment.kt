package space.pixelsg.tutuor.splash.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import space.pixelsg.tutuor.app.Router
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.databinding.WelcomeFragmentBinding

class WelcomeFragment : BindingFragment<WelcomeFragmentBinding>({ inflater, container ->
    WelcomeFragmentBinding.inflate(inflater, container, false)
}) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {
            Router.Auth.launchSingleTop(
                requireContext(), bundleOf(
                    "signIn" to true
                )
            )
        }

        binding.signUpBtn.setOnClickListener {
            Router.Auth.launchSingleTop(
                requireContext(), bundleOf(
                    "signUp" to true
                )
            )
        }
    }

}