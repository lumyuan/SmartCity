package com.example.smartcity.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TitleFragmentPagerView(private val fragments: ArrayList<Fragment>, private val titles: MutableList<CharSequence>, fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(
        fragmentManager
    ) {
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}