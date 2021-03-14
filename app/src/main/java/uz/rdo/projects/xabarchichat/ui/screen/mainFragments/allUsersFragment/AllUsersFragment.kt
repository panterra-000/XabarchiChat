package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.allUsersFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.FragmentAllUsersBinding
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.UsersAdapter
import uz.rdo.projects.xabarchichat.utils.extensions.showToast

@AndroidEntryPoint
class AllUsersFragment : Fragment() {

    lateinit var binding: FragmentAllUsersBinding
    private val viewModel: AllUsersViewModel by viewModels()
    lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllUsersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadObservers()
    }


    private fun loadViews() {
        adapter = UsersAdapter()
        adapter.submitUsers(listOf())
        binding.rvPersons.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPersons.adapter = adapter
        adapter.addContactClickCallback { newUser ->
            viewModel.addContact(newUser)
        }
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.getAllUsers()
        viewModel.allUsers.observe(this, allUsersObservers)
        viewModel.isAddedContact.observe(this, isAddedContactObserver)
    }

    private val allUsersObservers = Observer<List<User>> { myContacts ->
        adapter.submitUsers(myContacts)
    }

    private val isAddedContactObserver = Observer<Boolean> { isAddedNewContact ->
        if (isAddedNewContact) {
            showToast("ContactAdded")
        }
    }

}