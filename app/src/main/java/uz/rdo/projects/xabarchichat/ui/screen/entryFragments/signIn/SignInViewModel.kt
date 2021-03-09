package uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signIn

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.repositories.SignInRepository

class SignInViewModel @ViewModelInject constructor(
    private val repository: SignInRepository
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _isSignIn = MutableLiveData<Boolean>()
    val isSignIn: LiveData<Boolean> get() = _isSignIn

    private val _isResetPasswordToEmail = MutableLiveData<Boolean>()
    val isResetPasswordToEmail: LiveData<Boolean> get() = _isResetPasswordToEmail


    fun signIn(email: String, password: String) {
        repository.signIn(email, password) { isSignInM ->
            _isSignIn.value = isSignInM
        }
    }

    fun resetPasswordToEmail(email: String) {
        repository.resetPasswordToEmail(email) { isReset ->
            _isResetPasswordToEmail.value = isReset
        }
    }

}