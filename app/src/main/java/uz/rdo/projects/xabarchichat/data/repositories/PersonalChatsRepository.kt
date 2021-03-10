package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.data.models.ChatModel
import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface PersonalChatsRepository {
    fun getPersonalChatList(personalChatsCallback: SingleBlock<List<ChatModel>>)
}