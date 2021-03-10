package uz.rdo.projects.xabarchichat.data.localStorage

import android.content.Context
import uz.rdo.projects.xabarchichat.data.models.User

class LocalStorage private constructor(context: Context) {
    companion object {
        @Volatile
        lateinit var instance: LocalStorage
            private set

        fun init(context: Context) {
            instance =
                LocalStorage(
                    context
                )
        }
    }

    private val pref = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)

    var isFirst: Boolean by BooleanPreference(pref, true)
    var firebaseID: String by StringPreference(pref, "none")


    fun clear() {
        pref.edit().clear().apply()
    }
}