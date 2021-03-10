package uz.rdo.projects.xabarchichat.data.models

data class ChatModel(
    val chatId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val lastMessage: MessageModel? = null,
)
