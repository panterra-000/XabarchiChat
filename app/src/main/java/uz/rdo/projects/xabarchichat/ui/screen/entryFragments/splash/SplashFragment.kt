package uz.rdo.projects.xabarchichat.ui.screen.entryFragments.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.databinding.FragmentSplashBinding
import uz.rdo.projects.xabarchichat.ui.screen.activities.entry.EntryActivity
import uz.rdo.projects.xabarchichat.ui.screen.activities.main.MainActivity
import javax.inject.Inject


@Suppress("DEPRECATION")
@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var storage: LocalStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed(
            Runnable {
                checkUserId()
            }, 3000
        )
    }

    private fun checkUserId() {
        if (storage.firebaseID != "none") {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            (requireActivity() as EntryActivity).finish()
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }
    }
}