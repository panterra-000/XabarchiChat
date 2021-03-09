package uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signIn

import com.google.firebase.auth.FirebaseAuth
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.repositories.SignInRepository
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val storage: LocalStorage
) : SignInRepository {

    override fun signIn(email: String, password: String, isSignInCallback: SingleBlock<Boolean>) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isSignInCallback.invoke(true)
                storage.firebaseID = firebaseAuth.currentUser!!.uid
            } else {
                isSignInCallback.invoke(false)
            }
        }
    }

    override fun resetPasswordToEmail(
        email: String,
        isResetPasswordCallback: SingleBlock<Boolean>
    ) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                isResetPasswordCallback.invoke(true)
            } else {
                isResetPasswordCallback.invoke(false)
            }
        }
    }
}