package com.example.smartcity.activities

import androidx.fragment.app.Fragment
import com.example.smartcity.R
import com.example.smartcity.activities.fragments.*
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.databinding.ActivityMainBinding
import com.example.smartcity.ui.NavigationBar
import com.example.smartcity.ui.adapters.FragmentPagerView

class MainActivity : BaseActivity() {
    private val binding by binding(ActivityMainBinding::inflate)

    /*首页、全部服务、智慧党建、新闻、个人中心*/
    override fun initView() {
        super.initView()
        
        //初始化滑动窗体
        binding.mainViewpager.apply {
            val fragments = ArrayList<Fragment>().apply {
                add(HomeFragment.newInstance())
                add(AllServiceFragment.newInstance())
                add(SmartGroupBuildFragment.newInstance())
                add(NewsFragment.newInstance())
                add(MineStoreFragment.newInstance())
            }
            adapter = FragmentPagerView(fragments, supportFragmentManager)
        }

        //初始化底部导航栏
        val navigationItems = ArrayList<NavigationBar.NavigationItem>().apply {
            add(NavigationBar.NavigationItem(R.mipmap.ic_launcher, "首页"))
            add(NavigationBar.NavigationItem(R.mipmap.ic_launcher, "全部服务"))
            add(NavigationBar.NavigationItem(R.mipmap.ic_launcher, "智慧党建"))
            add(NavigationBar.NavigationItem(R.mipmap.ic_launcher, "新闻"))
            add(NavigationBar.NavigationItem(R.mipmap.ic_launcher, "个人中心"))
        }
        binding.navigationBar.bindData(binding.mainViewpager, navigationItems)
    }

}