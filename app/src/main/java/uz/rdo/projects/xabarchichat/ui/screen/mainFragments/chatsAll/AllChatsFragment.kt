package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsAll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.rdo.projects.xabarchichat.databinding.FragmentAllChatsBinding

class AllChatsFragment : Fragment() {

    lateinit var binding: FragmentAllChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllChatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
    }

    private fun loadViews() {


    }


}