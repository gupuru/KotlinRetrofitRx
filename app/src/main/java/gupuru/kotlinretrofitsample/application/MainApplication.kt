package gupuru.kotlinretrofitsample.application

import android.app.Application
import com.facebook.stetho.Stetho

open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

    }


}