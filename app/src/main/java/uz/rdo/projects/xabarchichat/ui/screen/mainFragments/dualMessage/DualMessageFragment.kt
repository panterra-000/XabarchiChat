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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.data.models.User
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
    lateinit var myFirebaseUser: User
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
        viewModel.getFirebaseUser()
        viewModel.getAllMessages(args.receiverContact)

        viewModel.firebaseUserData.observe(this, firebaseUserDataObserver)
        viewModel.allMessages.observe(this, allMessagesObserver)
        viewModel.isSendMessage.observe(this, isSendMessageObserver)
    }


    private val firebaseUserDataObserver = Observer<User> { firebaseUser ->
        if (firebaseUser != null) {
            myFirebaseUser = firebaseUser
        }
    }

    private val allMessagesObserver = Observer<List<MessageModel>> { messages ->
        adapter.submitMessages(messages)
        binding.rvMessage.scrollToPosition(adapter.itemCount - 1)
        viewModel.toBeSeenMessages(args.receiverContact)
    }

    private val isSendMessageObserver = Observer<Boolean> { isSend ->
        if (isSend) {
            showToast("+++ ___ +++")
        }
    }


    private fun loadViews() {

        binding.apply {


            btnAttachFile.setOnClickListener {
                showToast("adwdawdawdad")
                exampleGetMessage()
            }

            txtNameReceiver.text = args.receiverContact.username
            txtLastSeenTime.text = args.receiverContact.lastSeenTime.toString()
            adapter = DualChatAdapter(storage.firebaseID)
            adapter.submitMessages(listOf())
            binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
            binding.rvMessage.adapter = adapter
            adapter.onclickCallback {
                showToast("text: ${it.messageText}  \nisSeen: ${it.isSeen}  ")
            }

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
            viewModel.sendMessage(messageModel = messageModel, receiverUser = args.receiverContact)
            binding.etMessage.setText("")
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnect()

    }


    private fun exampleGetMessage() {
        val refMessage = FirebaseDatabase.getInstance().reference.child("MessageList")
            .child("RRno8uJqBIchQ1TsfGbHpEbLnVA2").child("mdAhINOsNCO3L3TdmmT6NFihaDg2")
            .child("Messages").child("-MVhQeCABO1lCDQ--aYI")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val mModel = snapshot.getValue(MessageModel::class.java)
                    if (mModel != null) {
                        val str = mModel.messageText + " ---- --- isSeen : " + mModel.isSeen
                        binding.etMessage.setText(str)
                    }
                }
            })
    }


}