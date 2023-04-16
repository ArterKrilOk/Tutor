package space.pixelsg.tutuor.splash.ui.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.app.Router
import space.pixelsg.tutuor.common.BindingFragment
import space.pixelsg.tutuor.common.UiUtils.repeatOnCreated
import space.pixelsg.tutuor.databinding.SplashFragmentBinding
import space.pixelsg.tutuor.splash.ui.activity.SplashActivity
import javax.inject.Inject

class SplashFragment : BindingFragment<SplashFragmentBinding>({ inflater, container ->
    SplashFragmentBinding.inflate(inflater, container, false)
}) {
    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as SplashActivity).component.splashSubcomponent.inject(this)

        repeatOnCreated {
            viewModel.authed.collect {
                if (it) Router.Main.launchSingleTop(requireContext())
                else findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
            }
        }
    }
}