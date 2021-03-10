package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.contacts

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.ContactsRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val storage: LocalStorage
) : ContactsRepository {


    override fun getContacts(contactsCallback: SingleBlock<List<User>>) {
        val refContacts =
            firebaseDatabase.reference.child("ContactList").child(storage.firebaseID)

        val contactList: ArrayList<User> = ArrayList()

        refContacts!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(allusersDatabase: DataSnapshot) {
                contactList.clear()
                if (allusersDatabase.exists()) {

                    for (user_data_item in allusersDatabase.children) {

                        val user: User? = user_data_item.getValue(User::class.java)
                        if (user != null) {
                            contactList.add(user)
                        }
                    }
                    contactsCallback.invoke(contactList)
                }
            }
        })
    }

    override fun addContact(username: String, isAddedContactCallback: SingleBlock<Boolean>) {
        TODO("Not yet implemented")
    }

    override fun deleteContact(user: User, isDeletedContactCallback: SingleBlock<Boolean>) {

        val refContacts =
            firebaseDatabase.reference.child("ContactList").child(firebaseAuth.currentUser!!.uid)


        refContacts.child("${user.username}__${user.uid}")
            .removeValue().addOnCompleteListener { deleteTask ->
                if (deleteTask.isSuccessful) {
                    isDeletedContactCallback.invoke(true)
                } else {
                    isDeletedContactCallback.invoke(false)
                }
            }

    }

    override fun inviteFriends() {
        TODO("Not yet implemented")
    }

}