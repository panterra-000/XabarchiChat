package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface SignInRepository {
    fun signIn(
        email: String,
        password: String,
        isSignInCallback: SingleBlock<Boolean>
    )

    fun resetPasswordToEmail(
        email: String,
        isResetPasswordCallback: SingleBlock<Boolean>
    )

}