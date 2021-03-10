package uz.rdo.projects.xabarchichat.data.models

data class ChatModel(
    val chatId: String = "",
    val senderUser: User,
    val receiverUser: User,
    val messageModel: MessageModel
)
