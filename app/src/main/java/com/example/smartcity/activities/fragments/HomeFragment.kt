package com.example.smartcity.activities.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Outline
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartcity.activities.NewsListActivity
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.databinding.FragmentHomeBinding
import com.example.smartcity.model.HomeModel
import com.example.smartcity.net.pojo.ServiceBean
import com.example.smartcity.ui.adapters.*
import com.example.smartcity.utils.FastFunctionUtils
import com.example.smartcity.utils.LogUtils
import com.youth.banner.indicator.CircleIndicator

class HomeFragment : BaseFragment() {

    private val binding by binding(FragmentHomeBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeModel
    private lateinit var handler: Handler
    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)
        handler = Handler(activity.mainLooper)
        viewModel = ViewModelProvider(this)[HomeModel::class.java]

        //搜索事件
        binding.gotoNews.setOnClickListener {
            val title = binding.searchEditor.text.toString().trim()
            activity.startActivity(Intent(activity, NewsListActivity::class.java).apply {
                this.putExtra("title", title)
            })
        }

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
            indicator = CircleIndicator(this.context)

            //绘制圆角
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, 30F)
                }
            }
            this.clipToOutline = true
        }
        viewModel.bannerData.observe(this){
            binding.banner.adapter = HomeGlideLoaderAdapter(it.rows!!)
        }
        viewModel.getBannerData()

        //设置轮播图比例
        binding.banner.post {
            binding.banner.apply {
                this.layoutParams.height = getBannerHeight(binding.banner.width)
                this.requestLayout()
            }
        }

        //加载服务
        binding.serviceList.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        }
        viewModel.serviceData.observe(this) {
            val rows = it.rows!!
            val list = ArrayList<ServiceBean>()
            for(i in 0 until 5){
                list.add(rows[i])
            }
            list.add(ServiceBean().apply {
                this.serviceName = "全部服务"
            })
            binding.serviceList.adapter = HomeServiceAdapter(list)
        }
        viewModel.getServiceData("N")

        //加载新闻资讯分类
        viewModel.newsClassifyData.observe(this){
            run{
                val titles = ArrayList<CharSequence>()
                val fragments = ArrayList<Fragment>()
                it.data?.forEach { bean->
                    fragments.add(HomeNewsFragment.newInstance(bean.id.toString()))
                    titles.add(bean.name.toString())
                }
                binding.homeNewsViewPager.apply {
                    this.adapter = TitleFragmentPagerView(fragments, titles, activity.supportFragmentManager)
                }
                binding.tabLayout.setupWithViewPager(binding.homeNewsViewPager)
            }
        }
        viewModel.getNewsClassify()
    }

    override fun loadData(activity: AppCompatActivity) {
        super.loadData(activity)
        //界面重载时关闭软键盘
        binding.searchEditor.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            requestFocus()
            setOnClickListener {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
                //唤起键盘
                handler.postDelayed({
                    try {
                        val inputMethodManager =
                            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.showSoftInput(this, 0)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }, 200)
            }
        }
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