package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.allUsersFragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.AllUsersRepository

class AllUsersViewModel @ViewModelInject constructor(
    private val repository: AllUsersRepository
) : ViewModel() {


    private val _allUsers = MutableLiveData<List<User>>()
    val allUsers: LiveData<List<User>> get() = _allUsers

    private val _isAddedContact = MutableLiveData<Boolean>()
    val isAddedContact: LiveData<Boolean> get() = _isAddedContact

    fun getAllUsers() {
        repository.getAllUsers { allUsersM ->
            _allUsers.value = allUsersM

        }
    }

    fun addContact(newUser: User) {
        repository.addContact(newUser) { isAddedNewContact ->
            _isAddedContact.value = isAddedNewContact
        }
    }

}