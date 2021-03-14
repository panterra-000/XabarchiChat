package uz.rdo.projects.xabarchichat.utils.extensions

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.showToast(string: String) {
    Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
}

fun Fragment.pickImageChooserIntent(REQUEST_CODE: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(Intent.createChooser(intent, "Pick Image"), REQUEST_CODE)
}