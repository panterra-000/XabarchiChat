package uz.rdo.projects.xabarchichat.data.firebase

import com.google.firebase.database.FirebaseDatabase
import uz.rdo.projects.xabarchichat.data.models.User

class AppDatabase private constructor() {
    private val database = FirebaseDatabase.getInstance()
    private val dbRootRef = database.reference
    private val userNode = dbRootRef.child("Users")

    fun saveUser(user: User, onSuccess: ((Boolean) -> Unit)? = null) {
        userNode.child("${user.uid}").setValue(user)
            .addOnCompleteListener {
                onSuccess?.invoke(it.isSuccessful)
            }
    }

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (appDatabase == null)
                appDatabase =
                    AppDatabase()
            return appDatabase!!
        }
    }
}