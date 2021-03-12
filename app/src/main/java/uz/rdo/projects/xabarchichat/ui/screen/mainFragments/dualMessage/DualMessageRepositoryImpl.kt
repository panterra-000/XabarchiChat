package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.DualMessageRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class DualMessageRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val storage: LocalStorage
) : DualMessageRepository {

    lateinit var myFirebaseUser: User

    override fun getFirebaseUser(getFirebaseUserCallback: SingleBlock<User>?) {
        val refFirebaseUser =
            firebaseDatabase.reference.child("Users").child(storage.firebaseID)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            myFirebaseUser = user
                            getFirebaseUserCallback?.invoke(user)
                        }
                    }
                })
    }

    override fun getAllMessages(
        receiver: User,
        allDualMessagesCallback: SingleBlock<List<MessageModel>>
    ) {
        val allMessages: ArrayList<MessageModel> = ArrayList()

        val id = storage.firebaseID
        val refAllMessages =
            firebaseDatabase.reference.child("MessageList").child(id).child(receiver.uid)
                .child("Messages")

        refAllMessages!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(allMessagesDb: DataSnapshot) {
                allMessages.clear()
                for (user_data_item in allMessagesDb.children) {

                    val messageModel: MessageModel? =
                        user_data_item.getValue(MessageModel::class.java)
                    if (messageModel != null) {
                        allMessages.add(messageModel)
                    }
                }
                allDualMessagesCallback.invoke(allMessages)
            }
        })
    }

    override fun sendMessage(
        messageModel: MessageModel,
        receiverUser: User,
        isSentMessageCallback: SingleBlock<Boolean>
    ) {

        val messageIDKey = firebaseDatabase.reference.push().key

        val messageHashMap = HashMap<String, Any?>()
        messageHashMap["messageID"] = messageIDKey
        messageHashMap["messageText"] = messageModel.messageText
        messageHashMap["senderID"] = messageModel.senderID
        messageHashMap["receiverID"] = messageModel.receiverID
        messageHashMap["sendDate"] = messageModel.sendDate
        messageHashMap["imageMessageURL"] = messageModel.imageMessageURL
        messageHashMap["isSeen"] = messageModel.isSeen

        firebaseDatabase.reference.child("MessageList").child(messageModel.senderID)
            .child(messageModel.receiverID)
            .child("Messages").child(messageIDKey!!).setValue(messageHashMap)
            .addOnCompleteListener { addToSChList ->
                if (addToSChList.isSuccessful) {
                    firebaseDatabase.reference.child("MessageList").child(messageModel.receiverID)
                        .child(messageModel.senderID)
                        .child("Messages").child(messageIDKey!!).setValue(messageHashMap)
                        .addOnCompleteListener { addToRChList ->
                            if (addToRChList.isSuccessful) {

                                val chatId = firebaseDatabase.reference.push().key

                                val chatHashMap = HashMap<String, Any?>()
                                chatHashMap["chatId"] = chatId
                                chatHashMap["senderUser"] = myFirebaseUser
                                chatHashMap["receiverUser"] = receiverUser
                                chatHashMap["messageModel"] = messageModel

                                firebaseDatabase.reference.child("PersonalChats")
                                    .child(storage.firebaseID).child(receiverUser.uid)
                                    .setValue(chatHashMap)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {

                                            val chatSecondHashMap = HashMap<String, Any?>()
                                            chatSecondHashMap["chatId"] = chatId
                                            chatSecondHashMap["receiverUser"] = myFirebaseUser
                                            chatSecondHashMap["senderUser"] = receiverUser
                                            chatSecondHashMap["messageModel"] = messageModel

                                            firebaseDatabase.reference.child("PersonalChats")
                                                .child(receiverUser.uid).child(storage.firebaseID)
                                                .setValue(chatSecondHashMap)
                                                .addOnCompleteListener { taskChat ->
                                                    if (taskChat.isSuccessful) {
                                                        isSentMessageCallback.invoke(true)
                                                    }
                                                }
                                        }
                                    }

                            } else {
                                isSentMessageCallback.invoke(false)
                            }
                        }
                } else {
                    isSentMessageCallback.invoke(false)
                }
            }
    }

    override fun deleteMessage(
        messageModel: MessageModel,
        isDeletedMessageCallback: SingleBlock<Boolean>
    ) {
        TODO("Not yet implemented")
    }

    override fun uploadLastMessage(
        senderUser: User,
        receiverUser: User,
        singleBlock: SingleBlock<Boolean>
    ) {
        val refMessages =
            firebaseDatabase.reference.child("MessageList").child(senderUser.uid)
                .child(receiverUser.uid).child("Messages")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (messageData in snapshot.children) {
                            val messageModel = messageData.getValue(MessageModel::class.java)
                            if (messageModel!!.senderID == senderUser.uid) {
                                val uploadHashMap = HashMap<String, Any>()
                                uploadHashMap["messageID"] = messageModel!!.messageID
                                uploadHashMap["messageText"] = messageModel!!.messageText
                                uploadHashMap["senderID"] = messageModel!!.senderID
                                uploadHashMap["receiverID"] = messageModel!!.receiverID
                                uploadHashMap["sendDate"] = messageModel!!.sendDate
                                uploadHashMap["imageMessageURL"] = messageModel.imageMessageURL
                                uploadHashMap["isSeen"] = true

                                val refUploadSingleMessage =
                                    firebaseDatabase.reference.child("MessageList")
                                        .child(senderUser.uid).child(receiverUser.uid)
                                        .child("Messages")
                                        .child(messageModel.messageID).setValue(uploadHashMap)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {


                                            }
                                        }
                            }
                        }
                    }
                })
    }

    override fun messagesToBeSeenUpload(
        senderUser: User,
        receiverUser: User,
        toBeSeenCallback: SingleBlock<Boolean>
    ) {
        val refMessages = firebaseDatabase.reference.child("Messages")
        val refMyMessagesList = refMessages.child(senderUser.uid)
        val refMessagesWithPartner = refMyMessagesList.child(receiverUser.uid).child("Messages")
        val refPartnerMessagesWithMe =
            refMessages.child(receiverUser.uid).child(senderUser.uid).child("Messages")


        refMessagesWithPartner
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (messageData in snapshot.children) {
                        val messageModel = messageData.getValue(MessageModel::class.java)
                        if (messageModel != null) {
                            if (messageModel.senderID == receiverUser.uid) {
                                val isSeenHashMap = HashMap<String, Boolean>()
                                isSeenHashMap["isSeen"] = true
                                refMessagesWithPartner.child(messageModel.messageID).child("isSeen")
                                    .setValue(isSeenHashMap).addOnCompleteListener { isSeenTask ->
                                        if (isSeenTask.isSuccessful) {
                                            refPartnerMessagesWithMe.child(messageModel.messageID)
                                                .child("isSeen").setValue(isSeenHashMap)
                                                .addOnCompleteListener { isSeenPartnerTask ->
                                                    if (isSeenPartnerTask.isSuccessful) {
                                                        toBeSeenCallback.invoke(true)
                                                    }
                                                }
                                        }
                                    }
                            }
                        }
                    }
                }
            })

    }

}