package uz.rdo.projects.xabarchichat.data.models

data class MessageModel(
    val messageID: String = "",
    val messageText: String = "",
    val senderID: String = "",
    val receiverID: String = "",
    val sendDate: Long = 0L,
    var imageMessageURL: String = "",
    var isSeen: Boolean = false
) {
    fun getIsSeen(): Boolean {
        return this.isSeen
    }

    fun setIsSeen(
        isSeen: Boolean
    ) {
        this.isSeen = isSeen
    }
}

