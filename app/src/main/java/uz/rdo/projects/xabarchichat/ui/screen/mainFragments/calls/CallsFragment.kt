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
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.data.localStorage.LocalStorage
import uz.rdo.projects.xabarchichat.data.models.User
import uz.rdo.projects.xabarchichat.databinding.FragmentCallsBinding
import javax.inject.Inject

@AndroidEntryPoint
class CallsFragment : Fragment() {

    lateinit var binding: FragmentCallsBinding

    @Inject
    lateinit var storage: LocalStorage

    @Inject
    lateinit var mAuth: FirebaseAuth

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


        var refUser = FirebaseDatabase.getInstance().reference
            .child("Users").child(storage.firebaseID)

        //   binding.txtFbUser.text = "${firebaseUser.uid}"

        refUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

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