package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.FragmentDualMessageBinding
import uz.rdo.projects.xabarchichat.ui.adapters.recycler.DualChatAdapter
import uz.rdo.projects.xabarchichat.utils.CHOOSER_REQUEST_CODE
import uz.rdo.projects.xabarchichat.utils.extensions.*
import uz.rdo.projects.xabarchichat.utils.media.AppMediaPlayer
import uz.rdo.projects.xabarchichat.utils.media.AppVoiceRecorder
import uz.rdo.projects.xabarchichat.utils.time.getCurrentDateTime
import javax.inject.Inject

@AndroidEntryPoint
class DualMessageFragment : Fragment() {

    lateinit var binding: FragmentDualMessageBinding
    lateinit var adapter: DualChatAdapter

    lateinit var mediaRecorder: MediaRecorder

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
        loadVoicePlayerButtons()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun loadObservers() {
        viewModel.getFirebaseUser()
        viewModel.getAllMessages(args.receiverContact)

        viewModel.firebaseUserData.observe(this, firebaseUserDataObserver)
        viewModel.allMessages.observe(this, allMessagesObserver)
        viewModel.isSendMessage.observe(this, isSendMessageObserver)
        viewModel.sendPictureData.observe(this, sendPictureDataObserver)
    }

    private val sendPictureDataObserver = Observer<String> { sentPictureURL ->
        showToast(sentPictureURL)
        binding.progressBar.hideView()
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setButtonClicks() {


        binding.apply {

            btnSend.setOnClickListener {
                sendMessage()
            }

            btnAttachFile.setOnClickListener {
                pickImageChooserIntent(CHOOSER_REQUEST_CODE)
            }

            btnPrev.setOnClickListener {
                findNavController().popBackStack()
            }

            btnCancelSendAudio.setOnClickListener {
                cvCreatedAudio.hideView()
            }

            btnSendAudio.setOnClickListener {
                cvCreatedAudio.hideView()
            }

            btnRecordAudio.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        AppVoiceRecorder.startRecord(viewModel.getMessageKey())
                        btnRecordAudio.setImageResource(R.drawable.ic_mic_open)
                    }
                    MotionEvent.ACTION_UP -> {
                        btnRecordAudio.setImageResource(R.drawable.ic_mic)
                        cvCreatedAudio.showView()
                        AppVoiceRecorder.stopRecord() { file, messageKey ->
                            showToast(file.absolutePath)
                            etMessage.setText(file.absolutePath)
                            AppMediaPlayer.prepareMediaPlayer(file.absolutePath)
                        }
                    }
                }

                true
            }

            etMessage.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim() == "") {
                        isSendTextMessage(false)
                    } else {
                        isSendTextMessage(true)
                    }
                }
            })
        }
    }

    private fun loadVoicePlayerButtons() {
        binding.apply {
            btnPlayAudio.setOnClickListener {
                AppMediaPlayer.playMyVoice()
            }

            btnPauseAudio.setOnClickListener {
                AppMediaPlayer.pauseMyVoice()
            }

            btnStopAudio.setOnClickListener {
                AppMediaPlayer.stopMyVoice()
            }
        }
    }

    private fun isSendTextMessage(btnSendMessageVisibility: Boolean) {
        if (btnSendMessageVisibility) {
            binding.btnSend.showView()
            binding.btnRecordAudio.hideView()
        } else {
            binding.btnSend.inVisibleView()
            binding.btnRecordAudio.showView()
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
                imageMessageURL = "",
                sendDate = getCurrentDateTime(),
                isSeen = false
            )
            viewModel.sendMessage(messageModel = messageModel, receiverUser = args.receiverContact)
            binding.etMessage.setText("")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            viewModel.sendPicture(
                uri = data.data!!,
                receiverUser = args.receiverContact
            )

            binding.progressBar.showView()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppVoiceRecorder.releaseRecorder()
        AppMediaPlayer.releaseMediaPlayer()
    }


}