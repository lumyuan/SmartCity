package com.example.smartcity.core

import com.example.smartcity.R
import com.example.smartcity.SmartCity
import com.example.smartcity.bean.SmartCitySettingsBean
import com.example.smartcity.utils.FileUtils
import com.google.gson.Gson
import java.io.File

object SettingsConfigLoader {

    private val dir = "${FileUtils.rootFileDir}/configs"
    private const val fileName = "settings.json"
    private val gson = Gson()

    init {
        val file = File(dir)
        if (!file.exists()){
            file.mkdirs()
        }
    }

    fun load(): SmartCitySettingsBean{
        val text = FileUtils.readText("$dir/$fileName")
        return if (text != null){
            gson.fromJson(text, SmartCitySettingsBean::class.java)
        }else {
            val bean = SmartCitySettingsBean().apply {
                this.dns = SmartCity.application.getString(R.string.dns)
                this.port = SmartCity.application.getString(R.string.default_port)
            }
            save(bean)
            bean
        }
    }

    fun save(smartCitySettingsBean: SmartCitySettingsBean){
        FileUtils.writeText("$dir/$fileName", gson.toJson(smartCitySettingsBean))
    }
}