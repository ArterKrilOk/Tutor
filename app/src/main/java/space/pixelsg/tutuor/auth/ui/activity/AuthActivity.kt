package space.pixelsg.tutuor.auth.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.app.App
import space.pixelsg.tutuor.auth.di.AuthComponent
import space.pixelsg.tutuor.auth.di.DaggerAuthComponent
import space.pixelsg.tutuor.databinding.ActivityAuthBinding


class AuthActivity : AppCompatActivity() {
    internal val component: AuthComponent by lazy {
        DaggerAuthComponent.builder()
            .dataComponent((application as App).dataComponent)
            .build()
    }

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val navController = binding.navHostFragment.findNavController()

        intent?.extras?.let {
            when {
                it.containsKey("signUp") -> navController.navigate(R.id.signUpFragment)
                it.containsKey("signIn") -> navController.navigate(R.id.signInFragment)
            }
            intent = null
        }
    }


}