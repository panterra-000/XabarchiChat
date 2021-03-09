package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.FragmentContactsBinding
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.ContactsAdapter
import uz.rdo.projects.xabarchichat.ui.dialogs.DeleteContactDialog
import uz.rdo.projects.xabarchichat.ui.screen.activities.main.MainActivity
import uz.rdo.projects.xabarchichat.utils.extensions.hideView
import uz.rdo.projects.xabarchichat.utils.extensions.showToast
import uz.rdo.projects.xabarchichat.utils.extensions.showView

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by viewModels()

    lateinit var binding: FragmentContactsBinding
    lateinit var adapter: ContactsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as MainActivity).binding.bottomMenuNav.showView()
        loadViews()
        loadObservers()
        loadButtons()
    }

    private fun loadButtons() {
        binding.btnAdd.setOnClickListener {
            goToAllUsersFragment()
        }
    }

    private fun goToAllUsersFragment() {
        (requireActivity() as MainActivity).binding.bottomMenuNav.hideView()
        findNavController().navigate(R.id.action_contactsFragment_to_allUsersFragment)
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.getContacts()
        viewModel.contacts.observe(this, contactsObserver)
        viewModel.deleteContact.observe(this, deleteContactObserver)
    }


    private val contactsObserver = Observer<List<User>> { myContacts ->
        if (myContacts.isEmpty()) {
            adapter.submitContacts(arrayListOf())
        } else {
            adapter.submitContacts(myContacts)
        }
        showToast("size : ${myContacts.size}")

    }

    private val deleteContactObserver = Observer<Boolean> { isDeleted ->
        if (isDeleted) {
            showToast(getString(R.string.contact_deleted))
        }
    }


    private fun loadViews() {
        adapter = ContactsAdapter()
        adapter.submitContacts(listOf())
        binding.rvPersons.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPersons.adapter = adapter
        setupFilterSpinner()

        adapter.deleteContactCallback { contact ->
            val deleteDialog = DeleteContactDialog(requireActivity(), contact)
            deleteDialog.show()
            deleteDialog.setOnclickDeleteCallback { username ->
                viewModel.deleteContact(username)
            }
        }

        adapter.contactClickCallback { contact ->
            var receiverContact = contact
            val action =
                ContactsFragmentDirections.actionContactsFragmentToDualMessageFragment(
                    receiverContact
                )
            findNavController().navigate(action)
            (requireActivity() as MainActivity).binding.bottomMenuNav.hideView()
        }
    }

    private fun setupFilterSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spn_filter_items,
            R.layout.sort_spn_item
        ).also { spnAdapter ->
            spnAdapter.setDropDownViewResource(R.layout.sort_spn_drop_down_item)
            binding.spnFilterContacts.adapter = spnAdapter
        }
    }


}