package uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import uz.rdo.projects.xabarchichat.databinding.FragmentChatsBinding
import uz.rdo.projects.xabarchichat.R
import uz.rdo.projects.xabarchichat.ui.adapters.viewPager.ViewPagerAdapter
import uz.rdo.projects.xabarchichat.ui.screen.activities.main.MainActivity
import uz.rdo.projects.xabarchichat.utils.extensions.hideView
import uz.rdo.projects.xabarchichat.utils.extensions.showView


class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    lateinit var vpAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as MainActivity).binding.bottomMenuNav.showView()
        loadViews()
    }

    private fun loadViews() {
        vpAdapter = ViewPagerAdapter(requireActivity())
        binding.vpChats.adapter = vpAdapter

        TabLayoutMediator(binding.tabLayout, binding.vpChats) { tab, position ->
            when (position) {
                0 -> {
                    tab.setText(R.string.all_chats)
                }
                1 -> {
                    tab.setText(R.string.personals)
                }
                2 -> {
                    tab.setText(R.string.groups)
                }
            }
        }.attach()
    }


    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
        }
    }
}