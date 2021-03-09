package uz.rdo.projects.xabarchichat.ui.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.DialogDeleteContactBinding
import uz.rdo.projects.xabarchichat.utils.SingleBlock

class DeleteContactDialog(private val activity: Activity, private val user: User) :
    AlertDialog(activity) {

    private var _binding: DialogDeleteContactBinding? = null
    private val binding: DialogDeleteContactBinding
        get() = _binding ?: throw NullPointerException("View wasn't created")

    private var listenClick: SingleBlock<User>? = null

    init {
        _binding = DialogDeleteContactBinding.inflate(layoutInflater)
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadViews()
    }

    private fun loadViews() {
        binding.txtNameContact.text = user.username

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnOkDelete.setOnClickListener {
            listenClick?.invoke(user)
            dismiss()
        }
    }

    fun setOnclickDeleteCallback(f: SingleBlock<User>) {
        listenClick = f
    }

}