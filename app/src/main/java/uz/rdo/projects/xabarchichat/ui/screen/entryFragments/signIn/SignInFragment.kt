package uz.rdo.projects.xabarchichat.ui.screen.entryFragments.signIn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.databinding.FragmentSignInBinding
import uz.rdo.projects.xabarchichat.ui.screen.activities.entry.EntryActivity
import uz.rdo.projects.xabarchichat.ui.screen.activities.main.MainActivity
import uz.rdo.projects.xabarchichat.utils.extensions.hideView
import uz.rdo.projects.xabarchichat.utils.extensions.showToast
import uz.rdo.projects.xabarchichat.utils.extensions.showView

@AndroidEntryPoint
class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadObservers()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.isSignIn.observe(this, signInObserver)
        viewModel.isResetPasswordToEmail.observe(this, resetPasswordObserver)
    }

    private val signInObserver = Observer<Boolean> { isSignIn ->
        binding.progressBar.hideView()
        if (isSignIn) {
            goToMainActivity()
        } else {
            showToast(getString(R.string.sign_in_error_error))
        }
    }

    private val resetPasswordObserver = Observer<Boolean> { isReset ->
        binding.progressBar.hideView()
        if (isReset) {
            binding.btnResetPassword.text = getString(R.string.the_password_was_sent_your_email)
        } else {
            showToast(getString(R.string.send_error_error))
        }
    }

    private fun loadViews() {
        binding.btnGoToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnSignIn.setOnClickListener {
            signIn()
        }

        binding.btnResetPassword.setOnClickListener {
            sendPasswordToEmail()
        }
    }

    private fun signIn() {

        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        when {
            email == "" -> {
                showToast(getText(R.string.please_write_email) as String)
            }
            password == "" -> {
                showToast(getText(R.string.please_write_password) as String)
            }
            else -> {
                binding.progressBar.showView()
                viewModel.signIn(email, password)
            }
        }
    }

    private fun sendPasswordToEmail() {
        val email = binding.etEmail.text.toString().trim()
        if (email == "") {
            showToast(getString(R.string.please_write_email))
        } else {
            binding.progressBar.showView()
            viewModel.resetPasswordToEmail(email)
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        requireContext().startActivity(intent)
        (requireActivity() as EntryActivity).finish()
    }
}