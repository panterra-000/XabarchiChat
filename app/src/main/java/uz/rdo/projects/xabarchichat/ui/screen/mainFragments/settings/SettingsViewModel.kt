package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.SettingsRepository

class SettingsViewModel @ViewModelInject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _signOutData = MutableLiveData<Boolean>()
    val signOutData: LiveData<Boolean> get() = _signOutData

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    fun signOut() {
        repository.signOut() { oldUserId ->
            _signOutData.value = oldUserId
        }
    }

    fun getUserData() {
        repository.getUserData() { myData ->
            _userData.value = myData
        }
    }

}