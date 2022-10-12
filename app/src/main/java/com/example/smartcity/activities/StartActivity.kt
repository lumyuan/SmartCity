package com.example.smartcity.activities

import android.os.Handler
import com.example.smartcity.base.BaseActivity
import com.example.smartcity.databinding.ActivityStartBinding
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.utils.FastFunctionUtils
import com.example.smartcity.utils.LogUtils

class StartActivity : BaseActivity() {

    private val binding by binding(ActivityStartBinding::inflate)

    override fun initView() {
        super.initView()
        binding.root.tag = "start activity"
        val mutableData = SmartCitySettingsModel.smartCitySettings
        val bean = mutableData.value!!
        LogUtils.print(bean)
        Handler(mainLooper).postDelayed({
            if (bean.isFirstStart){
                mutableData.value = bean
                FastFunctionUtils.startActivity(this, NavigationActivity::class.java)
            }else {
                FastFunctionUtils.startActivity(this, MainActivity::class.java)
            }
            finish()
        }, 1500)
    }

}