package com.example.smartcity

import android.app.Application
import com.example.smartcity.bean.SmartCitySettingsBean
import com.example.smartcity.core.MutableData
import com.example.smartcity.core.SettingsConfigLoader
import com.example.smartcity.model.SmartCitySettingsModel

class SmartCity: Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        //初始化全局设置类
        SmartCitySettingsModel.smartCitySettings.value = SettingsConfigLoader.load()
        //注册设置观察者
        SmartCitySettingsModel.smartCitySettings.observe(object : MutableData.Observer<SmartCitySettingsBean>{
            override fun onChanged(data: SmartCitySettingsBean?) {
                SettingsConfigLoader.save(data!!)
            }
        })
    }

}