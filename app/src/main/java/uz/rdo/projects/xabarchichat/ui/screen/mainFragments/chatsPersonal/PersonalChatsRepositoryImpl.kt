package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsPersonal

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.ChatModel
import uz.rdo.projects.xabarchichat.data.repositories.PersonalChatsRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class PersonalChatsRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val storage: LocalStorage
) : PersonalChatsRepository {

    override fun getPersonalChatList(personalChatsCallback: SingleBlock<List<ChatModel>>) {

        val chats = ArrayList<ChatModel>()
        firebaseDatabase.reference.child("PersonalChats").child(storage.firebaseID)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    chats.clear()
                    for (pChatSnapshot in snapshot.children) {
                        val pChat = pChatSnapshot.getValue(
                            ChatModel::class.java
                        )
                        if (pChat != null) {
                            chats.add(pChat)
                        }
                    }
                    personalChatsCallback.invoke(chats)
                }
            })
    }
}