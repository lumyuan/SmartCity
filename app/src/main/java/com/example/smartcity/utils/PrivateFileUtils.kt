package com.example.smartcity.utils

import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object PrivateFileUtils {
    val dir = "${FileUtils.rootFileDir}/external/self/0"
    val externalCropDir = "${FileUtils.rootExternalStorageDir}/DCIM/CROP"
    init {
        val file = File(dir)
        if(!file.exists()) {
            file.mkdirs()
        }
    }

    fun copy(inputStream: InputStream, name: String): File {
        val bytes = FileUtils.read(inputStream)
        val path = "$dir/$name"
        val file = File(path)
        FileUtils.write(FileOutputStream(file), bytes)
        return file
    }

    //获取裁剪保存地址uri
    fun cropPick(name: String): Uri {
        val parent = File(externalCropDir)
        if (!parent.exists()){
            parent.mkdirs()
        }
        val file = File(parent, name)
        if (!file.exists()){
            file.createNewFile()
        }
        return Uri.fromFile(file)
    }
}