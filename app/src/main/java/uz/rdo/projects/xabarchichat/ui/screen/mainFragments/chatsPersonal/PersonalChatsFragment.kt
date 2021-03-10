package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsPersonal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import uz.rdo.projects.xabarchichat.databinding.FragmentPersonalChatsBinding
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.ChatAdapter
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.ContactsAdapter

class PersonalChatsFragment : Fragment() {

    lateinit var binding: FragmentPersonalChatsBinding
    lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalChatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadObservers()
    }

    private fun loadObservers() {

    }

    private fun loadViews() {
        adapter = ChatAdapter()
        adapter.submitContacts(listOf())
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChat.adapter = adapter
    }

}