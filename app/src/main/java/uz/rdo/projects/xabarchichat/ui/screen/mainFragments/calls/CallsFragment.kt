package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.calls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.FragmentCallsBinding


class CallsFragment : Fragment() {

    lateinit var binding: FragmentCallsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()

    }

    private fun loadViews() {

        var firebaseUser = FirebaseAuth.getInstance().currentUser

        var refUser = FirebaseDatabase.getInstance().reference
            .child("Users").child(firebaseUser!!.uid)

        //   binding.txtFbUser.text = "${firebaseUser.uid}"

        refUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: User? = snapshot.getValue(User::class.java)
                    val desc = "userID : ${user?.uid} \n" +
                            "username : ${user?.username} "
                    binding.txtFbUser.text = desc
                }
            }
        })
    }
}