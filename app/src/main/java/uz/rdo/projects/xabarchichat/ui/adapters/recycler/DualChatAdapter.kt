package uz.rdo.projects.xabarchichat.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.databinding.LeftChatItemBinding
import uz.rdo.projects.xabarchichat.databinding.RightChatItemBinding
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import uz.rdo.projects.xabarchichat.utils.extensions.hideView
import uz.rdo.projects.xabarchichat.utils.extensions.showView
import uz.rdo.projects.xabarchichat.utils.loadImageForURL
import uz.rdo.projects.xabarchichat.utils.time.convertLongToTime

class DualChatAdapter(private val myId: String) :
    RecyclerView.Adapter<DualChatAdapter.BaseViewHolder>() {
    var messages: ArrayList<MessageModel> = ArrayList()

    var listenClick: SingleBlock<MessageModel>? = null

    companion object {
        private const val TYPE_SENDER = 0
        private const val TYPE_RECEIVER = 1
    }


    override fun getItemViewType(position: Int): Int {
        val id = messages[position].senderID
        return when (id) {
            myId -> TYPE_SENDER
            else -> TYPE_RECEIVER
        }
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_SENDER -> SenderViewHolder(
                RightChatItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_RECEIVER -> ReceiverViewHolder(
                LeftChatItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    inner class SenderViewHolder(private val binding: RightChatItemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind() {
            binding.apply {
                val message = messages[adapterPosition]
                if (message.imageMessageURL != "") {
                    imgMessage.showView()
                    txtMessage.hideView()
                    loadImageForURL(message.imageMessageURL, imgMessage)
                } else {
                    imgMessage.hideView()
                    txtMessage.showView()
                    txtMessage.text = message.messageText
                }
                txtTimeOf.text = convertLongToTime(message.sendDate)

                if (message.isSeen) {
                    imgSeen.setImageResource(R.drawable.ic_all_read)
                } else {
                    imgSeen.setImageResource(R.drawable.ic_sent)
                }

                root.setOnClickListener {
                    listenClick?.invoke(message)
                }
            }
        }
    }

    inner class ReceiverViewHolder(private val binding: LeftChatItemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind() {
            binding.apply {
                binding.apply {
                    val message = messages[adapterPosition]
                    if (message.imageMessageURL != "") {
                        imgMessage.showView()
                        txtMessage.hideView()
                        loadImageForURL(message.imageMessageURL, imgMessage)
                    } else {
                        imgMessage.hideView()
                        txtMessage.showView()
                        txtMessage.text = message.messageText
                    }
                    txtTimeOf.text = convertLongToTime(message.sendDate)
                }
            }
        }
    }


    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind()
    }

    fun submitMessages(_messages: List<MessageModel>) {
        messages.clear()
        messages.addAll(_messages)
        notifyDataSetChanged()
    }


    fun onclickCallback(f: SingleBlock<MessageModel>) {
        listenClick = f
    }


}