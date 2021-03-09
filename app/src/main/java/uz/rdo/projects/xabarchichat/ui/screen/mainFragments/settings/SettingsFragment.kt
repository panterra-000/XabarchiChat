package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.databinding.FragmentSettingsBinding
import uz.rdo.projects.xabarchichat.ui.screen.activities.entry.EntryActivity

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadObservers()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.signOutData.observe(this, signOutObserver)

    }

    private val signOutObserver = Observer<Boolean> { signOutTask ->
        if (signOutTask) {
            backToWelcomeActivity()
        }
    }

    private fun loadViews() {
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
    }

    private fun backToWelcomeActivity() {
        val intent = Intent(requireContext(), EntryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

}