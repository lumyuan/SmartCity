package com.example.smartcity.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter<T: ViewBinding>(private val viewBindings: MutableList<T>) : PagerAdapter(){
    override fun getCount(): Int {
        return viewBindings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val root = viewBindings[position].root
        container.addView(root)
        return root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewBindings[position].root)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return viewBindings[position].root.tag.toString()
    }
}