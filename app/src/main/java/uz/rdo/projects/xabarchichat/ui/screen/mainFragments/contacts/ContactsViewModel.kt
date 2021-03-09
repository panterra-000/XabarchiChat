package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.ContactsRepository

class ContactsViewModel @ViewModelInject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _contacts = MutableLiveData<List<User>>()
    val contacts: LiveData<List<User>> get() = _contacts

    private val _deleteContact = MutableLiveData<Boolean>()
    val deleteContact: LiveData<Boolean> get() = _deleteContact

    fun getContacts() {
        repository.getContacts { myContacts ->
            _contacts.value = myContacts
        }
    }

    fun deleteContact(user: User) {
        repository.deleteContact(user) { isDeleted ->
            _deleteContact.value = isDeleted
        }

    }


}