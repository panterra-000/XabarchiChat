package uz.rdo.projects.xabarchichat.data.models

data class ChatModel(
    val chatId: String = "",
    val senderId: String = "",
    val receiver: User,
    val messageModel: MessageModel
)
