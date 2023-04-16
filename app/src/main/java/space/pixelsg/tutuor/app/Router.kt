package space.pixelsg.tutuor.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import space.pixelsg.tutuor.auth.ui.activity.AuthActivity
import space.pixelsg.tutuor.main.activity.MainActivity
import space.pixelsg.tutuor.splash.ui.activity.SplashActivity

object Router {
    object Auth : Route {
        override val destination: Class<*> = AuthActivity::class.java
    }

    object Main : Route {
        override val destination: Class<*> = MainActivity::class.java
    }

    object Splash : Route {
        override val destination: Class<*> = SplashActivity::class.java
    }
}

interface Route {
    val destination: Class<*>

    fun launch(context: Context, bundle: Bundle? = null) {
        context.startActivity(getIntent(context, bundle))
    }

    fun launchSingleTop(context: Context, bundle: Bundle? = null) {
        context.startActivity(getIntent(context, bundle).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    fun getIntent(context: Context, bundle: Bundle? = null) = Intent(
        context, destination
    ).apply {
        if (bundle != null) putExtras(bundle)
    }
}