package uz.rdo.projects.xabarchichat.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.rdo.projects.xabarchichat.data.models.MessageModel
import uz.rdo.projects.xabarchichat.databinding.LeftChatItemBinding
import uz.rdo.projects.xabarchichat.databinding.RightChatItemBinding

class DualChatAdapter(private val myId: String) :
    RecyclerView.Adapter<DualChatAdapter.BaseViewHolder>() {
    var messages: ArrayList<MessageModel> = ArrayList()

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
        }
    }

    inner class ReceiverViewHolder(private val binding: LeftChatItemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind() {

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


}