package com.example.smartcity.utils

import android.os.Environment
import com.example.smartcity.SmartCity
import java.io.*
import java.nio.charset.Charset

object FileUtils {

    val rootFileDir = SmartCity.application.filesDir.absolutePath
    val rootExternalStorageDir = Environment.getExternalStorageDirectory().absolutePath
    val rootExternalFileDir = File(Environment.getExternalStorageDirectory(), "/Android/data/${SmartCity.application.packageName}/files").absolutePath

    fun readBytes(path: String): ByteArray {
        val fileInputStream = FileInputStream(path)
        val bufferedInputStream = BufferedInputStream(fileInputStream)
        val readBytes = bufferedInputStream.readBytes()
        bufferedInputStream.close()
        fileInputStream.close()
        return readBytes
    }


    fun writeBytes(path: String, bytes: ByteArray){
        val fileOutputStream = FileOutputStream(path)
        val bufferedOutputStream = BufferedOutputStream(fileOutputStream)
        bufferedOutputStream.write(bytes)
        bufferedOutputStream.close()
        fileOutputStream.close()
    }

    fun readText(path: String): String? {
        return try {
            String(readBytes(path), Charset.defaultCharset())
        }catch (e: IOException){
            e.printStackTrace()
            null
        }
    }

    fun writeText(path: String, content: String): Boolean{
        return try {
            writeBytes(path, content.toByteArray(Charset.defaultCharset()))
            true
        }catch (e: IOException){
            e.printStackTrace()
            false
        }
    }
}