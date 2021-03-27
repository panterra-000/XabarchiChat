package uz.rdo.projects.xabarchichat.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


fun senRequestPermissions() {
    if (ActivityCompat.checkSelfPermission(
            APP_ACTIVITY,
            Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            APP_ACTIVITY,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            111
        )
    } else {
        showToast("ddd")
    }
}