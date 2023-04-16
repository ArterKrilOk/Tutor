package space.pixelsg.tutuor.app

import android.app.Application
import android.content.Context
import com.google.android.material.color.DynamicColors
import space.pixelsg.tutuor.app.di.AppModule
import space.pixelsg.tutuor.app.di.DaggerAppComponent
import space.pixelsg.tutuor.data.di.DaggerDataComponent
import space.pixelsg.tutuor.data.di.DataComponent

class App : Application() {
    val dataComponent: DataComponent by lazy {
        DaggerDataComponent
            .builder()
            .appComponent(
                DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()
            )
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        SplitCompat.install(this)
    }
}