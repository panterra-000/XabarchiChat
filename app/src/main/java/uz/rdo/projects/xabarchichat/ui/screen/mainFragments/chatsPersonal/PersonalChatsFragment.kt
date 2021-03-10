package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsPersonal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.rdo.projects.xabarchichat.databinding.FragmentPersonalChatsBinding

class PersonalChatsFragment : Fragment() {

    lateinit var binding: FragmentPersonalChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalChatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadObservers()

    }

    private fun loadObservers() {


    }

    private fun loadViews() {



    }


}