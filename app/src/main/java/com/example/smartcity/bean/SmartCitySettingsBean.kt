package com.example.smartcity.bean

class SmartCitySettingsBean {
    var dns: String? = null
    var port: String? = null
    var isFirstStart = true
    var debug = true
    override fun toString(): String {
        return "SmartCitySettingsBean(dns=$dns, port=$port, isFirstStart=$isFirstStart, debug=$debug)"
    }

}