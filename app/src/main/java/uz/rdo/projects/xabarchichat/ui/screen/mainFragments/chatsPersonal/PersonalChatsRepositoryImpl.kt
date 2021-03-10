package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsPersonal

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

    override fun getPersonalChatList(singleBlock: SingleBlock<List<ChatModel>>) {

    }
}