package uz.rdo.projects.xabarchichat.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.channels.consumesAll
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.models.ChatModel
import uz.rdo.projects.xabarchichat.databinding.ItemChatBinding
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import uz.rdo.projects.xabarchichat.utils.extensions.bindItem
import uz.rdo.projects.xabarchichat.utils.extensions.hideView
import uz.rdo.projects.xabarchichat.utils.extensions.showView

class ChatAdapter(
    private val myID: String
) :
    RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    var chats: ArrayList<ChatModel> = ArrayList()

    private var listenContactClick: SingleBlock<ChatModel>? = null

    inner class ChatHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = bindItem {
            binding.apply {
                val chat = chats[adapterPosition]
                txtReceiverName.text = chat.receiverUser!!.username
                txtLastMessage.text = chat.messageModel!!.messageText
                val message = chat.messageModel

                if (message.senderID == myID) {
                    if (message.isSeen) {
                        imgIsSeen.setImageResource(R.drawable.ic_all_read)
                    } else {
                        imgIsSeen.setImageResource(R.drawable.ic_sent)
                    }
                } else {
                    if (message.isSeen) {
                        imgIsSeen.hideView()
                    } else {
                        imgIsSeen.showView()
                        imgIsSeen.setImageResource(R.drawable.ic_new_message)
                    }
                }




                if (chat.receiverUser!!.status == "online") {
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

    fun submitChats(_chats: List<ChatModel>) {
        chats.clear()
        chats.addAll(_chats)
        notifyDataSetChanged()
    }


    fun contactClickCallback(f: SingleBlock<ChatModel>) {
        listenContactClick = f
    }

}




