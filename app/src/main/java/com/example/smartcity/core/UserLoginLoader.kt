package com.example.smartcity.core

import com.example.smartcity.bean.UserLoginBean
import com.example.smartcity.db.UserLoginDB
import com.example.smartcity.utils.FileUtils
import com.example.smartcity.utils.SimpleEncryptUtil
import com.google.gson.Gson
import java.io.File

object UserLoginLoader {
    private val dir = "${FileUtils.rootFileDir}/configs"
    private const val fileName = "login user"
    private val gson = Gson()

    init {
        val file = File(dir)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    fun load(): UserLoginBean? {
        val readText = FileUtils.readText("$dir/$fileName")
        return if (readText != null){
            gson.fromJson(SimpleEncryptUtil.decode(readText), UserLoginBean::class.java)
        }else {
            null
        }
    }

    fun save(userLoginBean: UserLoginBean){
        FileUtils.writeText("$dir/$fileName", SimpleEncryptUtil.encrypt(gson.toJson(userLoginBean)))
    }

    fun remove(){
        File("$dir/$fileName").delete()
    }
}