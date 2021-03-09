package uz.rdo.projects.xabarchichat.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        LocalStorage.init(this)
    }

    companion object {
        lateinit var instance: App
    }
}