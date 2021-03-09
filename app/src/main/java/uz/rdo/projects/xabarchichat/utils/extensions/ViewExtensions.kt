package uz.rdo.projects.xabarchichat.utils.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Davronbek Raximjanov on 17-Feb-21
 **/

fun View.showView(){
    this.visibility = View.VISIBLE
}

fun View.hideView(){
    this.visibility = View.GONE
}

fun View.inVisible(){
    this.visibility = View.INVISIBLE
}

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    // To get the correct window token, lets first get the currently focused view
    var view = activity.currentFocus

    // To get the window token when there is no currently focused view, we have a to create a view
    if (view == null) {
        view = View(activity)
    }

    // hide the keyboard
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}