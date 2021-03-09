package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsGroup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.rdo.projects.xabarchichat.databinding.FragmentGroupChatsBinding


class GroupChatsFragment : Fragment() {

    lateinit var binding: FragmentGroupChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupChatsBinding.inflate(layoutInflater)
        return binding.root
    }
}