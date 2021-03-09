package uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signUp

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
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.databinding.FragmentSignUpBinding
import uz.rdo.projects.xabarchichat.ui.screen.activities.entry.EntryActivity
import uz.rdo.projects.xabarchichat.ui.screen.activities.main.MainActivity
import uz.rdo.projects.xabarchichat.utils.extensions.hideView
import uz.rdo.projects.xabarchichat.utils.extensions.showToast
import uz.rdo.projects.xabarchichat.utils.extensions.showView
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    @Inject
    lateinit var storage: LocalStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadObservers()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.firebaseID.observe(this, signUpObserver)

    }

    private val signUpObserver = Observer<String> { id ->
        if (id != "none") {
            storage.firebaseID = id
            binding.progressBar.hideView()
            var intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            (requireActivity() as EntryActivity).finish()
        }
    }

    private fun loadViews() {
        binding.btnSignUp.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val newUsername = binding.etUsername.text.toString().trim()
        val newEmail = binding.etEmail.text.toString().trim()
        val newPassword = binding.etPassword.text.toString().trim()

        when {
            newPassword.length < 7 -> {
                showToast(getString(R.string.password_max_seven_character))
            }
            newUsername == ""
            -> {
                showToast(
                    getString(R.string.please_write_username),
                )
            }
            newEmail == "" -> {
                showToast(
                    getString(R.string.please_write_email),
                )
            }
            newPassword == "" -> {
                showToast(
                    getString(R.string.please_write_password),
                )
            }
            else -> {
                binding.progressBar.showView()
                viewModel.signUpUser(
                    requireContext(),
                    newEmail,
                    newPassword,
                    newUsername
                )
            }
        }
    }

}