package uz.rdo.projects.xabarchichat.utils.time

import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*


fun Date.toMyString(
    format: String = "dd/MM/yyyy",
    locale: Locale = Locale.getDefault()
): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Long {
    return Calendar.getInstance().timeInMillis
}

fun getInterValDays(deadLineDay: Long, oldDay: Long): Long {
    val diff: Long = deadLineDay - oldDay
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    return hours / 24
}


fun DatePicker.getMilliseconds(): Long {
    val cal = Calendar.getInstance()
    cal[Calendar.DAY_OF_MONTH] = this.dayOfMonth
    cal[Calendar.MONTH] = this.month
    cal[Calendar.YEAR] = this.year
    return cal.timeInMillis
}


fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}

fun currentTimeToLong(): Long {
    return System.currentTimeMillis()
}

fun convertDateToLong(date: String): Long {
    val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return df.parse(date).time
}