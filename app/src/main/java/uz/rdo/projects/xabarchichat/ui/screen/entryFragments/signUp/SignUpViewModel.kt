package uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signUp

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.repositories.SignUpRepository

class SignUpViewModel @ViewModelInject constructor(
    private val repository: SignUpRepository
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _isSignUp = MutableLiveData<Boolean>()
    val isSignUp: LiveData<Boolean> get() = _isSignUp

    private val _firebaseID = MutableLiveData<String>()
    val firebaseID: LiveData<String> get() = _firebaseID

    fun signUpUser(context: Context, email: String, password: String, username: String) {
        repository.signUp(email = email, password = password, username = username) { id ->
            _firebaseID.value = id
        }
    }
}