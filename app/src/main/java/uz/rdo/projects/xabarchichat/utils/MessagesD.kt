package uz.rdo.projects.xabarchichat.utils

import android.widget.Toast
import uz.rdo.projects.xabarchichat.app.App

fun showToast(string: String) {
    Toast.makeText(App.instance, "$string", Toast.LENGTH_SHORT).show()
}