package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface AllUsersRepository {
    fun getAllUsers(allUsersCallback: SingleBlock<List<User>>)
    fun addContact(user: User, isAddedCallback: SingleBlock<Boolean>)
}