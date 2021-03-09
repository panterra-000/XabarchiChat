package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface SignUpRepository {
    fun signUp(
        email: String,
        password: String,
        username: String,
        isRegisterCallback: SingleBlock<String>
    )
}