package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.DualMessageRepository

class DualMessageViewModel @ViewModelInject constructor(
    private val repository: DualMessageRepository
) : ViewModel() {

    private val _allMessages = MutableLiveData<List<MessageModel>>()
    val allMessages: LiveData<List<MessageModel>> get() = _allMessages

    private val _isSendMessage = MutableLiveData<Boolean>()
    val isSendMessage: LiveData<Boolean> get() = _isSendMessage

    fun getAllMessages(receiver: User) {
        repository.getAllMessages(receiver) { messages ->
            _allMessages.value = messages
        }
    }

    fun sendMessage(messageModel: MessageModel) {
        repository.sendMessage(messageModel) { isSent ->
            _isSendMessage.value = isSent
        }
    }


}