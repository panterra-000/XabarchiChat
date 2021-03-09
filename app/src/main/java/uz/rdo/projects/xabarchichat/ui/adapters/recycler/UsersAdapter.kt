package uz.rdo.projects.xabarchichat.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.ItemUserBinding
import uz.rdo.projects.xabarchichat.utils.SingleBlock
import uz.rdo.projects.xabarchichat.utils.extensions.bindItem

class UsersAdapter(
) :
    RecyclerView.Adapter<UsersAdapter.UserHolder>() {

    private var listenAddContactClick: SingleBlock<User>? = null

    var users: ArrayList<User> = ArrayList()

    inner class UserHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = bindItem {
            binding.apply {
                val user = users[adapterPosition]
                txtName.text = user.username
                txtLastSeenTime.text = user.lastSeenTime.toString()
                //Picasso.get().load(user.profile).placeholder(R.drawable.ic_profile).into(imgProfile)
                root.setOnClickListener {

                }
                btnAdd.setOnClickListener {
                    listenAddContactClick?.invoke(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder = UserHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) = holder.bind()

    fun submitUsers(users_: List<User>) {
        users.clear()
        users.addAll(users_)
        notifyDataSetChanged()
    }

    fun addContactClickCallback(f: SingleBlock<User>) {
        listenAddContactClick = f
    }
}




