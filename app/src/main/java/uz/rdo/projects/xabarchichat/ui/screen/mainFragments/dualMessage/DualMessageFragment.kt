package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.databinding.FragmentDualMessageBinding
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.DualChatAdapter
import uz.rdo.projects.xabarchichat.utils.extensions.showToast
import uz.rdo.projects.xabarchichat.utils.time.getCurrentDateTime
import javax.inject.Inject

@AndroidEntryPoint
class DualMessageFragment : Fragment() {

    lateinit var binding: FragmentDualMessageBinding
    lateinit var adapter: DualChatAdapter

    @Inject
    lateinit var storage: LocalStorage

    private val viewModel: DualMessageViewModel by viewModels()

    val args: DualMessageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDualMessageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadObservers()
        loadViews()
        setButtonClicks()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.getAllMessages(args.receiverContact)
        viewModel.allMessages.observe(this, allMessagesObserver)
        viewModel.isSendMessage.observe(this, isSendMessageObserver)
    }

    private val allMessagesObserver = Observer<List<MessageModel>> { messages ->
        adapter.submitMessages(messages)
    }

    private val isSendMessageObserver = Observer<Boolean> { isSend ->
        if (isSend) {
            showToast("+++ ___ +++")
        }
    }


    private fun loadViews() {
        binding.apply {
            txtNameReceiver.text = args.receiverContact.username
            txtLastSeenTime.text = args.receiverContact.lastSeenTime.toString()
            adapter = DualChatAdapter(storage.firebaseID)
            adapter.submitMessages(listOf())
            binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
            binding.rvMessage.adapter = adapter
        }
    }

    private fun setButtonClicks() {
        binding.apply {
            btnSend.setOnClickListener {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        val messageTXT = binding.etMessage.text.toString().trim()
        if (messageTXT != "") {
            val messageModel = MessageModel(
                messageID = "",
                messageText = messageTXT,
                senderID = storage.firebaseID,
                receiverID = args.receiverContact.uid,
                imageMessageURL = getString(R.string.if_simple_message_send_CODE),
                sendDate = getCurrentDateTime(),
                isSeen = false
            )
            viewModel.sendMessage(messageModel)
        }
    }

}