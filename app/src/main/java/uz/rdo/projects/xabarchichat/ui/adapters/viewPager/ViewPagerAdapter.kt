package uz.rdo.projects.xabarchichat.ui.adapters.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsAll.AllChatsFragment
import uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsGroup.GroupChatsFragment
import uz.rdo.projects.xabarchichat.ui.screen.mainFragments.chatsPersonal.PersonalChatsFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val firstFragment = AllChatsFragment()
    private val secondFragment = PersonalChatsFragment()
    private val thirdFragment = GroupChatsFragment()

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> firstFragment
        1 -> secondFragment
        2 -> thirdFragment
        else -> firstFragment
    }
}