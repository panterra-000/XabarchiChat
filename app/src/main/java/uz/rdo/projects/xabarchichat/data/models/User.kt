package uz.rdo.projects.xabarchichat.data.models

data class User(
    var bioText: String = "",
    var email: String = "",
    var lastSeenTime: Long = 0,
    var profileImg: String = "",
    var status: String = "",
    var search: String = "",
    var uid: String = "",
    var username: String = ""
)
