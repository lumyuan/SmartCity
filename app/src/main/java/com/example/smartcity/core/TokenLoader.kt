package com.example.smartcity.core

import com.example.smartcity.utils.FileUtils
import com.example.smartcity.utils.SimpleEncryptUtil
import java.io.File

object TokenLoader {
    private val dir = "${FileUtils.rootFileDir}/configs"
    private const val fileName = "cookie"

    init {
        val file = File(dir)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    fun load(): String? {
        val file = File("$dir/$fileName")
        val readText = FileUtils.readText(file.absolutePath)
        return if (readText != null){
            SimpleEncryptUtil.decode(readText)
        }else {
            null
        }
    }

    fun save(token: String){
        FileUtils.writeText("$dir/$fileName", SimpleEncryptUtil.encrypt(token))
    }

    fun remove(){
        File("${dir}/${fileName}").delete()
    }
}