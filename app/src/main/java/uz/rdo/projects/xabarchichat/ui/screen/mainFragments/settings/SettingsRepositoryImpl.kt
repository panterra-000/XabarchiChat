package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.settings

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.repositories.SettingsRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val storage: LocalStorage

) : SettingsRepository {
    override fun signOut(isSignOutCallback: SingleBlock<Boolean>) {
        storage.firebaseID = "none"
        FirebaseAuth.getInstance().signOut()
        isSignOutCallback.invoke(true)
    }

}