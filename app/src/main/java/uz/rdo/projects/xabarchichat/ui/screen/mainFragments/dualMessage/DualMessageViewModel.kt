package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import android.net.Uri
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

    private val _sendPictureData = MutableLiveData<String>()
    val sendPictureData: LiveData<String> get() = _sendPictureData

    private val _firebaseUserData = MutableLiveData<User>()
    val firebaseUserData: LiveData<User> get() = _firebaseUserData

    private val _toBeSeenMessagesData = MutableLiveData<Boolean>()
    val toBeSeenMessageData: LiveData<Boolean> get() = _toBeSeenMessagesData


    fun getFirebaseUser() {
        repository.getFirebaseUser() { firebaseUser ->
            _firebaseUserData.value = firebaseUser
        }
    }


    fun getAllMessages(receiver: User) {
        repository.getAllMessages(receiver) { messages ->
            _allMessages.value = messages
        }
    }

    fun sendMessage(messageModel: MessageModel, receiverUser: User) {
        repository.sendMessage(messageModel = messageModel, receiverUser = receiverUser) { isSent ->
        }
    }

    fun toBeSeenMessages(receiverUser: User) {
        repository.messagesToBeSeenUpload(
            receiverUser = receiverUser
        ) { isSeenAllMessages ->
            _toBeSeenMessagesData.value = isSeenAllMessages
        }
    }


    fun sendPicture(uri: Uri, receiverUser: User) {
        repository.sendPicture(
            fileUri = uri,
            receiverUser = receiverUser,
        ) { isSendPicture ->
            _sendPictureData.value = isSendPicture
        }
    }

}