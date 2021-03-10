package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsPersonal

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
import uz.rdo.projects.xabarchichat.data.models.ChatModel
import uz.rdo.projects.xabarchichat.databinding.FragmentPersonalChatsBinding
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.ChatAdapter
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.ContactsAdapter

@AndroidEntryPoint
class PersonalChatsFragment : Fragment() {

    lateinit var binding: FragmentPersonalChatsBinding
    lateinit var adapter: ChatAdapter

    private val viewModel: PersonalChatsViewModel by viewModels()

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

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.getPersonalChats()
        viewModel.personalChats.observe(this, personalChatsObserver)
    }

    private val personalChatsObserver = Observer<List<ChatModel>> { personalChats ->
        adapter.submitChats(personalChats)
    }

    private fun loadViews() {
        adapter = ChatAdapter()
        adapter.submitChats(listOf())
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChat.adapter = adapter
    }


}