package uz.rdo.projects.xabarchichat.data.models

data class ChatModel(
    val chatId: String = "",
    val chatStatus: String = "",
    val senderUser: User? = null,
    val receiverUser: User? = null,
    val messageModel: MessageModel? = null

)
