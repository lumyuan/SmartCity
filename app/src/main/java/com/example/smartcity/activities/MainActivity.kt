package com.example.smartcity.activities

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartcity.R
import com.example.smartcity.activities.fragments.*
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.core.UserLoginLoader
import com.example.smartcity.databinding.ActivityMainBinding
import com.example.smartcity.model.GetUserModel
import com.example.smartcity.model.LoginModel
import com.example.smartcity.model.MainViewInternetModel
import com.example.smartcity.net.Repository
import com.example.smartcity.net.params.LoginBean
import com.example.smartcity.ui.NavigationBar
import com.example.smartcity.ui.adapters.FragmentPagerView
import com.example.smartcity.utils.FastFunctionUtils
import com.example.smartcity.utils.LogUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private val binding by binding(ActivityMainBinding::inflate)

    private lateinit var loginViewModel: LoginModel
    private lateinit var getUserModel: GetUserModel

    /*首页、全部服务、智慧党建、新闻、个人中心*/
    override fun initView() {
        super.initView()

        //获取外部存储权限
        FastFunctionUtils.getWritePermission(this)

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
            //设置缓存页面数量
            offscreenPageLimit = fragments.size
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

        loginViewModel = ViewModelProvider(this)[LoginModel::class.java]
        getUserModel = ViewModelProvider(this)[GetUserModel::class.java]

        //登录状态监听
        loginViewModel.token.observe(this){
            if (it.code == 200 && it.token != null){
                //获取用户信息
                getUserModel.getUser(it.token!!)
            }
        }

        //向外部共享组件
        MainViewInternetModel.putView("viewpager", binding.mainViewpager)
    }

    override fun onResume() {
        super.onResume()
        //自动登录
        autoLogin()
    }

    /**
     * 自动登录
     */
    private fun autoLogin(){
        val load = UserLoginLoader.load()
        if (load != null){
            loginViewModel.login(
                LoginBean().apply {
                    this.username = load.userName
                    this.password = load.password
                }
            )
        }else {
            getUserModel.userData.value = null
        }
    }
}