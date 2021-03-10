package uz.rdo.projects.xabarchichat.data.models

data class ChatModel(
    val chatId: String = "",
    val myID: String = "",
    val receiver: User,
    val messageModel: MessageModel
)
