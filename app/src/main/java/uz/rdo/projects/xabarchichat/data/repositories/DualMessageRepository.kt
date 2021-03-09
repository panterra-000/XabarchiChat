package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface DualMessageRepository {
    fun getAllMessages(allDualMessagesCallback: SingleBlock<List<MessageModel>>)
}