package com.example.smartcity.activities

import android.view.View
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.databinding.ActivityNavigationBinding
import com.example.smartcity.model.NavigationViewModel
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.ui.adapters.SimpleGlideLoaderAdapter
import com.example.smartcity.ui.dialogs.InternetSettingDialog
import com.example.smartcity.utils.FastFunctionUtils
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

class NavigationActivity : BaseActivity() {

    private val binding by binding(ActivityNavigationBinding::inflate)

    private lateinit var viewModel: NavigationViewModel
    private var pageSize = 0;
    override fun initView() {
        super.initView()
        binding.banner.apply {
            indicator = CircleIndicator(this@NavigationActivity)
            isAutoLoop(false)
            addOnPageChangeListener(object : OnPageChangeListener{
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(position: Int) {
                    if (position == pageSize - 1){
                        showView(View.VISIBLE)
                    }else {
                        showView(View.GONE)
                    }
                }

                override fun onPageScrollStateChanged(p0: Int) {

                }

            })
        }

        viewModel = initViewModel(NavigationViewModel::class.java)
        viewModel.pictureList.observe(this){
            binding.banner.adapter = SimpleGlideLoaderAdapter(it!!.rows!!)
            pageSize = it.rows!!.size
        }
        viewModel.getData()

        binding.goToMain.setOnClickListener {
            FastFunctionUtils.startActivity(this, MainActivity::class.java)
            val smartCitySettingsBean = SmartCitySettingsModel.smartCitySettings.value!!
            //设置不是首次启动
            smartCitySettingsBean.isFirstStart = false
            SmartCitySettingsModel.smartCitySettings.value = smartCitySettingsBean
            this.finish()
        }
        binding.internetSetting.setOnClickListener {
            val internetSettingDialog = InternetSettingDialog(this)
            internetSettingDialog.show()
        }
    }

    private fun showView(visibility: Int){
        binding.goToMain.visibility = visibility
        binding.internetSetting.visibility = visibility
    }
}