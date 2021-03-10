package uz.rdo.projects.xabarchichat.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.rdo.projects.xabarchichat.data.models.ChatModel
import uz.rdo.projects.xabarchichat.databinding.ItemChatBinding
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import uz.rdo.projects.xabarchichat.utils.extensions.bindItem

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




