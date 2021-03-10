package uz.rdo.projects.xabarchichat.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.models.ChatModel
import uz.rdo.projects.xabarchichat.databinding.ItemChatBinding
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import uz.rdo.projects.xabarchichat.utils.extensions.bindItem
import uz.rdo.projects.xabarchichat.utils.extensions.hideView
import uz.rdo.projects.xabarchichat.utils.extensions.showView

class ChatAdapter(
) :
    RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    var chats: ArrayList<ChatModel> = ArrayList()

    private var listenContactClick: SingleBlock<ChatModel>? = null

    inner class ChatHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = bindItem {
            binding.apply {
                val chat = chats[adapterPosition]
                txtReceiverName.text = chat.receiverUser.username
                txtLastMessage.text = chat.messageModel.messageText

                if (chat.messageModel.isSeen) {
                    imgIsSeen.setImageResource(R.drawable.ic_all_read)
                } else {
                    imgIsSeen.setImageResource(R.drawable.ic_sent)
                }

                if (chat.messageModel.senderID == chat.senderUser.uid) {
                    imgIsSeen.showView()
                } else {
                    imgIsSeen.hideView()
                }

                if (chat.receiverUser.status == "online") {
                    viewOnlineStatus.setBackgroundResource(R.drawable.online_back)
                } else {
                    viewOnlineStatus.setBackgroundResource(R.drawable.offline_back)
                }
                root.setOnClickListener {
                    listenContactClick?.invoke(chat)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder = ChatHolder(
        ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatHolder, position: Int) = holder.bind()

    fun submitContacts(_chats: List<ChatModel>) {
        chats.clear()
        chats.addAll(_chats)
        notifyDataSetChanged()
    }


    fun contactClickCallback(f: SingleBlock<ChatModel>) {
        listenContactClick = f
    }

}




