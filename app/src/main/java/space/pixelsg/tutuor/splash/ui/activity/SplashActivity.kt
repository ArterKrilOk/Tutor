package space.pixelsg.tutuor.splash.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.app.App
import space.pixelsg.tutuor.splash.di.DaggerSplashComponent
import space.pixelsg.tutuor.splash.di.SplashComponent


class SplashActivity : AppCompatActivity() {
    internal val component: SplashComponent by lazy {
        DaggerSplashComponent.builder()
            .dataComponent((application as App).dataComponent)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}