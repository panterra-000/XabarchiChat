package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.rdo.projects.xabarchichat.databinding.FragmentDualMessageBinding

class DualMessageFragment : Fragment() {

    lateinit var binding: FragmentDualMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDualMessageBinding.inflate(layoutInflater)
        return binding.root
    }
}