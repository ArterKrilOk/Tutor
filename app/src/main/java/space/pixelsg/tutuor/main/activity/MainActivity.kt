package space.pixelsg.tutuor.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.app.App
import space.pixelsg.tutuor.databinding.ActivityMainBinding
import space.pixelsg.tutuor.main.di.DaggerMainActivityComponent
import space.pixelsg.tutuor.main.di.MainActivityComponent

class MainActivity : AppCompatActivity() {

    val component: MainActivityComponent by lazy {
        DaggerMainActivityComponent.builder()
            .dataComponent((application as App).dataComponent)
            .build()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        Navigation.findNavController(this, R.id.main_nav_host_fragment).let { navController ->
            binding.bottomNavBar.setupWithNavController(navController)
        }
    }
}