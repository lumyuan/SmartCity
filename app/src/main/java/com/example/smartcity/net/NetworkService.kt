package com.example.smartcity.net

import com.example.smartcity.R
import com.example.smartcity.SmartCity
import com.example.smartcity.model.SmartCitySettingsModel

/**
 * 网络服务类
 * @author ssq
 */
object NetworkService {
    // 请求根地址
    private val BASE_URL = SmartCity.application.getString(R.string.dns)
    // 接口API服务(挂起)
    val api by lazy {
        ApiFactory.createService("${BASE_URL}:${SmartCitySettingsModel.smartCitySettings.value!!.port}", ApiService::class.java)
    }
}