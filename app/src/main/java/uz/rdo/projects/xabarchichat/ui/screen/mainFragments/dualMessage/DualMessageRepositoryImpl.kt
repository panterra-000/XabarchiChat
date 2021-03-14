package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.DualMessageRepository
import uz.rdo.projects.xabarchichat.utils.STATUS_PERSONAL
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class DualMessageRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
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
                                chatHashMap["chatStatus"] = STATUS_PERSONAL
                                chatHashMap["senderUser"] = myFirebaseUser
                                chatHashMap["receiverUser"] = receiverUser
                                chatHashMap["messageModel"] = messageHashMap

                                firebaseDatabase.reference.child("AllChatList")
                                    .child(storage.firebaseID).child(receiverUser.uid)
                                    .setValue(chatHashMap)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val chatSecondHashMap = HashMap<String, Any?>()
                                            chatSecondHashMap["chatId"] = chatId
                                            chatSecondHashMap["chatStatus"] = STATUS_PERSONAL
                                            chatSecondHashMap["receiverUser"] = myFirebaseUser
                                            chatSecondHashMap["senderUser"] = receiverUser
                                            chatSecondHashMap["messageModel"] = messageHashMap

                                            firebaseDatabase.reference.child("AllChatList")
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

    override fun sendPicture(
        fileUri: Uri,
        messageModel: MessageModel,
        receiverUser: User,
        isSentPictureCallback: SingleBlock<Boolean>
    ) {

        val storageReference = firebaseStorage.reference.child("Chat Images")

        val messageID = firebaseDatabase.reference.push().key
        val filePath = storageReference.child("$messageID.jpg")

        var uploadTask: StorageTask<*>
        uploadTask = filePath.putFile(fileUri)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation filePath.downloadUrl

        }).addOnCompleteListener { imageTask ->
            if (imageTask.isSuccessful) {
                val downloadUrl = imageTask.result
                val url = downloadUrl.toString()
                messageModel.imageMessageURL = url

                sendMessage(
                    messageModel = messageModel,
                    receiverUser = receiverUser
                ) { sendMessageTask ->
                    if (sendMessageTask) {
                        isSentPictureCallback.invoke(true)
                    } else {
                        isSentPictureCallback.invoke(false)
                    }
                }
            } else {
                isSentPictureCallback.invoke(false)
            }

        }
    }


    override fun deleteMessage(
        messageModel: MessageModel,
        isDeletedMessageCallback: SingleBlock<Boolean>
    ) {
    }


    override fun messagesToBeSeenUpload(
        receiverUser: User,
        toBeSeenCallback: SingleBlock<Boolean>
    ) {
        val id = storage.firebaseID
        val refMessages = firebaseDatabase.reference.child("MessageList")
        val refMyMessagesList = refMessages.child(id)
        val refMessagesWithPartner =
            refMyMessagesList.child(receiverUser.uid).child("Messages")
        val refPartnerMessagesWithMe =
            refMessages.child(receiverUser.uid).child(id).child("Messages")


        refMessagesWithPartner.addValueEventListener(object : ValueEventListener {
            var taskDone = false
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && !taskDone) {
                    var allMessagesUpdate = true
                    for (messageData in snapshot.children) {
                        val messageModel =
                            messageData.getValue(MessageModel::class.java)
                        if (messageModel != null) {
                            if (messageModel.senderID == receiverUser.uid) {
                                refMessagesWithPartner.child(messageModel.messageID)
                                    .child("isSeen")
                                    .setValue(true).addOnCompleteListener { change ->
                                        if (change.isSuccessful) {
                                            refPartnerMessagesWithMe.child(messageModel.messageID)
                                                .child("isSeen")
                                                .setValue(true)
                                                .addOnCompleteListener { change ->
                                                    if (!change.isSuccessful) {
                                                        allMessagesUpdate = false
                                                    }
                                                }
                                        } else {
                                            allMessagesUpdate = false
                                        }
                                    }
                            }
                        }
                    }


                    val refLastMessage =
                        firebaseDatabase.reference.child("AllChatList")
                            .child(storage.firebaseID)
                            .child(receiverUser.uid).child("messageModel")

                    val refLastMessagePartner =
                        firebaseDatabase.reference.child("AllChatList").child(receiverUser.uid)
                            .child(storage.firebaseID).child("messageModel")

                    val lastMessageIsSeenValueEventListener =
                        refLastMessage.addValueEventListener(object : ValueEventListener {
                            var lastMessageTaskDone = false

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists() && !lastMessageTaskDone) {
                                    val messageModel =
                                        snapshot.getValue(MessageModel::class.java)
                                    if (messageModel != null) {
                                        if (messageModel.senderID == receiverUser.uid) {
                                            refLastMessage.child("isSeen").setValue(true)
                                                .addOnCompleteListener { changeToSeen ->
                                                    if (changeToSeen.isSuccessful) {
                                                        refLastMessagePartner.child("isSeen")
                                                            .setValue(true)
                                                            .addOnCompleteListener { lastMessageSeenPartner ->
                                                                if (!lastMessageSeenPartner.isSuccessful) {
                                                                    allMessagesUpdate = false
                                                                }
                                                            }
                                                    }
                                                }
                                        }
                                    }
                                    lastMessageTaskDone = true
                                }
                            }
                        })

                    taskDone = true
                    toBeSeenCallback.invoke(allMessagesUpdate)
                }
            }
        })


    }

}

