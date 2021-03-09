package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.allUsersFragment

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.AllUsersRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class AllUsersRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : AllUsersRepository {

    override fun getAllUsers(allUsersCallback: SingleBlock<List<User>>) {

        val allContactList: ArrayList<User> = ArrayList()

        val refUsers = firebaseDatabase.reference.child("Users")

        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(allusersDatabase: DataSnapshot) {
                allContactList.clear()
                for (user_data_item in allusersDatabase.children) {

                    val user: User? = user_data_item.getValue(User::class.java)
                    if (user!!.uid != firebaseAuth.currentUser!!.uid) {
                        allContactList.add(user)
                    }
                }
                allUsersCallback.invoke(allContactList)
            }
        })
    }

    override fun addContact(newUser: User, isAddedCallback: SingleBlock<Boolean>) {

        val myFirebaseId = firebaseAuth.currentUser!!.uid

        val refContacts =
            firebaseDatabase.reference.child("ContactList").child(myFirebaseId)
                .child("${newUser.username}__${newUser.uid}")

        val userHashMap = HashMap<String, Any>()
        userHashMap["uid"] = newUser.uid
        userHashMap["email"] = newUser.email
        userHashMap["username"] = newUser.username
        userHashMap["profileImg"] = newUser.profileImg
        userHashMap["status"] = "offline"
        userHashMap["search"] = newUser.username.toLowerCase().trim()
        userHashMap["lastSeenTime"] = newUser.lastSeenTime
        userHashMap["bioText"] = newUser.bioText

        refContacts.updateChildren(userHashMap)
            .addOnCompleteListener { userAddTask ->
                if (userAddTask.isSuccessful) {
                    isAddedCallback.invoke(true)
                } else {
                    isAddedCallback.invoke(false)
                }
            }
    }
}