package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.dualMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import uz.rdo.projects.xabarchichat.databinding.FragmentDualMessageBinding

@AndroidEntryPoint
class DualMessageFragment : Fragment() {

    lateinit var binding: FragmentDualMessageBinding

    private val viewModel: DualMessageViewModel by viewModels()

    val args: DualMessageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDualMessageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()

    }

    private fun loadViews() {
        binding.apply {
            txtNameReceiver.text = args.receiverContact.username
            txtLastSeenTime.text = args.receiverContact.lastSeenTime.toString()
        }
    }

}