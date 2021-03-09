package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface DualMessageRepository {
    fun getAllMessages(receiver: User, allDualMessagesCallback: SingleBlock<List<MessageModel>>)
    fun sendMessage(messageModel: MessageModel, isSentMessageCallback: SingleBlock<Boolean>)
    fun deleteMessage(messageModel: MessageModel, isDeletedMessageCallback: SingleBlock<Boolean>)
}