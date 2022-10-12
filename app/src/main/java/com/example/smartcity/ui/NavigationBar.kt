package com.example.smartcity.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager
import com.example.smartcity.R

/**
 * 自定义底部导航栏
 */
class NavigationBar: LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL
    }

    var duration = 400L

    private fun getItem(id: Int, imageResourceId: Int, title: CharSequence, viewPager: ViewPager): LinearLayout {
        val view = View.inflate(context, R.layout.menu_navigation_item, null) as LinearLayout
        view.tag = id
        view.gravity = Gravity.CENTER
        val image = view.findViewById<ImageView>(R.id.icon)
        val text = view.findViewById<TextView>(R.id.title)
        image.setImageResource(imageResourceId)
        text.text = title
        view.setOnClickListener {
            viewPager.currentItem = id
        }
        return view
    }

    fun bindData(viewPager: ViewPager, data: MutableList<NavigationItem>){
        removeAllViews()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                selectItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        for (item in data.indices){
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            layoutParams.weight = 1F
            addView(getItem(item, data[item].imageResourceId!!, data[item].title!!, viewPager), layoutParams)
        }
        selectItem(viewPager.currentItem)
    }

    private fun selectItem(position: Int){
        var i = 0
        children.forEach {
            val imageView = it.findViewById<ImageView>(R.id.icon)
            val textView = it.findViewById<TextView>(R.id.title)
            if (i == position){
                selected(it, imageView, textView)
            }else {
                defaulted(it, imageView, textView)
            }
            i++
        }
    }

    @SuppressLint("ResourceType")
    private fun selected(rootView: View, imageView: ImageView, textView: TextView){
        imageView.setColorFilter(Color.parseColor(context.getString(R.color.theme)))
        textView.setTextColor(Color.parseColor(context.getString(R.color.theme)))
        scale(rootView, "scaleX", 1.2F)
        scale(rootView, "scaleY", 1.2F)
    }

    @SuppressLint("ResourceType")
    private fun defaulted(rootView: View, imageView: ImageView, textView: TextView){
        imageView.setColorFilter(Color.parseColor(context.getString(R.color.gray_400)))
        textView.setTextColor(Color.parseColor(context.getString(R.color.gray_400)))
        scale(rootView, "scaleX", 1F)
        scale(rootView, "scaleY", 1F)
    }

    private fun scale(view: View, type: String, scale: Float){
        val animator = ObjectAnimator.ofFloat(view, type, scale)
        animator.duration = duration
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    class NavigationItem {
        constructor()
        constructor(imageResourceId: Int?, title: CharSequence?) {
            this.imageResourceId = imageResourceId
            this.title = title
        }
        @NonNull
        var imageResourceId: Int? = null
        @NonNull
        var title: CharSequence? = null
    }

    interface OnSelectedListener{
        fun onSelected(position: Int)
    }
}