package com.example.smartcity.model

import com.example.smartcity.core.MutableData
import com.example.smartcity.core.SettingsConfigLoader

/**
 * 全局配置类
 */
object SmartCitySettingsModel {
    val smartCitySettings = MutableData(SettingsConfigLoader.load())

    fun getWorkUrl(): String {
        return "${smartCitySettings.value!!.dns}:${smartCitySettings.value!!.port}"
    }
}