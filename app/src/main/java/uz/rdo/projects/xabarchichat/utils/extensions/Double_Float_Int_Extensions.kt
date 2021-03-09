package uz.rdo.projects.xabarchichat.utils.extensions


fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun Float.format(digits: Int) = "%.${digits}f".format(this)