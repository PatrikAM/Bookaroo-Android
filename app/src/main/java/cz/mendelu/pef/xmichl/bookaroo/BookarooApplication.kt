package cz.mendelu.pef.xmichl.bookaroo

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookarooApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}