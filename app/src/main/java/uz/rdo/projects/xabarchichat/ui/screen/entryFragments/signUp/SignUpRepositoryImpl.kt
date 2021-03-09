package uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signUp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.data.repositories.SignUpRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import uz.rdo.projects.xabarchichat.utils.time.getCurrentDateTime
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val storage: LocalStorage
) : SignUpRepository {

    override fun signUp(
        email: String,
        password: String,
        username: String,
        firebaseIDCallback: SingleBlock<String>
    ) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var newUser = User(
                    uid = auth.currentUser!!.uid,
                    email = email,
                    username = username,
                    profileImg = "",
                    status = "offline",
                    search = username.trim().toLowerCase(),
                    bioText = "",
                    lastSeenTime = getCurrentDateTime()
                )

                val refUser = firebaseDatabase.reference.child("Users").child(newUser.uid)
                val userHashMap = HashMap<String, Any>()
                userHashMap["uid"] = newUser.uid
                userHashMap["email"] = newUser.email
                userHashMap["username"] = newUser.username
                userHashMap["profileImg"] = newUser.profileImg
                userHashMap["status"] = "offline"
                userHashMap["search"] = username.toLowerCase().trim()
                userHashMap["lastSeenTime"] = newUser.lastSeenTime
                userHashMap["bioText"] = newUser.bioText

                refUser.updateChildren(userHashMap).addOnCompleteListener { taskDatabase ->
                    if (taskDatabase.isSuccessful) {
                        storage.firebaseID = "${newUser.uid}"
                        firebaseIDCallback.invoke("${newUser.uid}")

                    } else {
                        firebaseIDCallback.invoke("none")
                    }
                }
            } else {
                firebaseIDCallback.invoke("none")
            }
        }
    }


}