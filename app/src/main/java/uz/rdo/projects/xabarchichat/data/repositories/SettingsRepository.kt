package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface SettingsRepository {
    fun signOut(isSignOutCallback: SingleBlock<Boolean>)
    fun getUserData(userDataCallback: SingleBlock<User>)
}