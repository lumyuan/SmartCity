package com.example.smartcity.net

import com.example.smartcity.model.SmartCitySettingsModel

/**
 * 网络服务类
 * @author ssq
 */
object NetworkService {
    // 接口API服务(挂起)
    val api by lazy {
        ApiFactory.createService(SmartCitySettingsModel.getWorkUrl(), ApiService::class.java)
    }
}