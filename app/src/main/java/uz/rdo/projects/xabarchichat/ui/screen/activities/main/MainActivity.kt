package uz.rdo.projects.xabarchichat.ui.screen.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.databinding.ActivityMainBinding
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.utils.APP_ACTIVITY

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
    }


}