package uz.rdo.projects.xabarchichat.data.models

data class MessageModel(
    val messageID: String = "",
    val messageText: String = "",
    val senderID: String = "",
    val receiverID: String = "",
    val sendDate: String = "",
    val isSeen: Boolean = false
)