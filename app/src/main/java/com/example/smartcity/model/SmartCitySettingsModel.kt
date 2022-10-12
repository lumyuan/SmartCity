package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.R
import com.example.smartcity.SmartCity
import com.example.smartcity.core.MutableData
import com.example.smartcity.core.SettingsConfigLoader

/**
 * 全局配置类
 */
object SmartCitySettingsModel {
    val smartCitySettings by lazy {
        MutableData(SettingsConfigLoader.load())
    }
}