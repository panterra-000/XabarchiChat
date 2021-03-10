package uz.rdo.projects.xabarchichat.ui.screen.activities.entry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.databinding.ActivityEntryBinding
import javax.inject.Inject

@AndroidEntryPoint
class EntryActivity : AppCompatActivity() {

    @Inject
    lateinit var storage: LocalStorage

    lateinit var binding: ActivityEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}