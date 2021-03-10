package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsPersonal

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.models.ChatModel
import uz.rdo.projects.xabarchichat.data.repositories.PersonalChatsRepository

class PersonalChatsViewModel @ViewModelInject constructor(
    private val repository: PersonalChatsRepository
) : ViewModel() {

    private val _personalChats = MutableLiveData<List<ChatModel>>()
    val personalChats: LiveData<List<ChatModel>> get() = _personalChats

    fun getPersonalChats() {
        repository.getPersonalChatList { allPersonalChats ->
            _personalChats.value = allPersonalChats

        }
    }
}
