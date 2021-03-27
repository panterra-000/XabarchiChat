package uz.rdo.projects.xabarchichat.ui.screen.activities.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.databinding.ActivityMainBinding
import uz.rdo.projects.xabarchichat.utils.APP_ACTIVITY
import uz.rdo.projects.xabarchichat.utils.senRequestPermissions
import uz.rdo.projects.xabarchichat.utils.showToast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadViews()
    }

    private fun loadViews() {
        val navController = findNavController(R.id.navHostMainFragment)
        binding.bottomMenuNav.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this
        senRequestPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showToast("OK !!!")
        }
    }

}