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
        return read(fileInputStream)
    }

    fun read(inputStream: InputStream): ByteArray {
        val bufferedInputStream = BufferedInputStream(inputStream)
        val readBytes = bufferedInputStream.readBytes()
        bufferedInputStream.close()
        inputStream.close()
        return readBytes
    }

    fun write(outputStream: OutputStream, bytes: ByteArray){
        val bufferedOutputStream = BufferedOutputStream(outputStream)
        bufferedOutputStream.write(bytes)
        bufferedOutputStream.close()
        outputStream.close()
    }

    fun writeBytes(path: String, bytes: ByteArray){
        val fileOutputStream = FileOutputStream(path)
        write(fileOutputStream, bytes)
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