package com.example.smartcity.activities.fragments

import android.graphics.Outline
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity.R
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.databinding.FragmentNewsBinding
import com.example.smartcity.model.HomeModel
import com.example.smartcity.model.NewsFragmentModel
import com.example.smartcity.ui.adapters.NewsGlideLoaderAdapter
import com.example.smartcity.ui.adapters.TitleFragmentPagerView
import com.example.smartcity.utils.FastFunctionUtils
import com.youth.banner.indicator.CircleIndicator

class NewsFragment : BaseFragment() {

    private val binding by binding(FragmentNewsBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsFragmentModel
    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)
        viewModel = ViewModelProvider(this)[NewsFragmentModel::class.java]

        //折叠布局监听器
        val identifier = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = activity.resources.getDimension(identifier)
        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val height = appBarLayout.height
            if (-verticalOffset >= height - statusBarHeight - binding.tabLayout.height - binding.tabLayout.marginTop){
                binding.bannerLayout.visibility = View.INVISIBLE
            }else {
                binding.bannerLayout.visibility = View.VISIBLE
            }
        }

        //加载轮播图
        binding.banner.apply {
            //绘制圆角
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, FastFunctionUtils.dp2px(10F).toFloat())
                }
            }
            this.clipToOutline = true
        }

        //加载轮播图
        viewModel.newsData.observe(this){
            it.rows?.let { list->
                binding.banner.adapter = NewsGlideLoaderAdapter(list)
            }
        }

        //设置轮播图比例
        binding.banner.post {
            binding.banner.apply {
                this.layoutParams.height = getBannerHeight(binding.banner.width)
                this.requestLayout()
            }
        }

        //加载新闻资讯分类
        viewModel.newsClassifyData.observe(this){
            run{
                val titles = ArrayList<CharSequence>()
                val fragments = ArrayList<Fragment>()
                it.data?.forEach { bean->
                    fragments.add(HomeNewsFragment.newInstance(bean.id.toString()))
                    titles.add(bean.name.toString())
                }
                binding.newsViewPager.apply {
                    this.adapter = TitleFragmentPagerView(fragments, titles, activity.supportFragmentManager)
                }
                binding.tabLayout.setupWithViewPager(binding.newsViewPager)
            }
        }
        viewModel.getNewsClassify()

        viewModel.getNewsListData()
    }

    /**
     * 按比例自适应轮播图高度
     */
    private fun getBannerHeight(width: Int): Int{
        return if (width == 0){
            getBannerHeight(width)
        }else {
            (binding.banner.width.toFloat() * (9F / 18F)).toInt()
        }
    }
}