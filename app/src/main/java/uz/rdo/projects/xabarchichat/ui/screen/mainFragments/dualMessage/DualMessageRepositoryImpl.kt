package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.data.repositories.DualMessageRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class DualMessageRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : DualMessageRepository {


    override fun getAllMessages(allDualMessagesCallback: SingleBlock<List<MessageModel>>) {

    }

}