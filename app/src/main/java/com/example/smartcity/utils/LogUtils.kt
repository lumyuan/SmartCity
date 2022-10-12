package com.example.smartcity.utils

import com.example.smartcity.model.SmartCitySettingsModel

object LogUtils {
    fun print(any: Any){
        if (SmartCitySettingsModel.smartCitySettings.value!!.debug){
            println(any)
        }
    }
}