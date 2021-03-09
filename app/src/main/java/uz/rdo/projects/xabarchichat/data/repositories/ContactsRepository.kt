package uz.rdo.projects.xabarchichat.data.repositories

import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.utils.SingleBlock

interface ContactsRepository {
    fun getContacts(contactsCallback: SingleBlock<List<User>>)
    fun addContact(username: String, isAddedContactCallback: SingleBlock<Boolean>)
    fun deleteContact(user: User, isDeletedContactCallback: SingleBlock<Boolean>)
    fun inviteFriends()
}