package com.sevcabel.sevcabelport.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.sevcabel.sevcabelport.adapters.SmartFragmentStatePagerAdapter

class BottomBarAdapter(fragmentManager: FragmentManager?) : SmartFragmentStatePagerAdapter(fragmentManager) {
    private val fragments: MutableList<Fragment> = ArrayList()

    fun addFragments(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}