package uz.rdo.projects.xabarchichat.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.ItemContactBinding
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import uz.rdo.projects.xabarchichat.utils.extensions.bindItem

class ContactsAdapter(
) :
    RecyclerView.Adapter<ContactsAdapter.UserHolder>() {

    var users: ArrayList<User> = ArrayList()

    private var listenDeleteContactClick: SingleBlock<User>? = null
    private var listenContactClick: SingleBlock<User>? = null


    inner class UserHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = bindItem {
            binding.apply {
                val user = users[adapterPosition]
                txtName.text = user.username
                txtLastSeenTime.text = user.lastSeenTime.toString()
                //Picasso.get().load(user.profile).placeholder(R.drawable.ic_profile).into(imgProfile)
                root.setOnClickListener {
                    listenContactClick?.invoke(user)
                }
                btnDelete.setOnClickListener {
                    listenDeleteContactClick?.invoke(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder = UserHolder(
        ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) = holder.bind()

    fun submitContacts(users_: List<User>) {
        users.clear()
        users.addAll(users_)
        notifyDataSetChanged()
    }

    fun deleteContactCallback(f: SingleBlock<User>) {
        listenDeleteContactClick = f
    }

    fun contactClickCallback(f: SingleBlock<User>) {
        listenContactClick = f
    }

}




