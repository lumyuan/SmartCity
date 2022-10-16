package com.example.smartcity.net

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    // OkHttpClient客户端
    private val mClient: OkHttpClient by lazy {
        newClient()
    }

    /**
     * 创建API Service接口实例
     */
    fun <T> createService(baseUrl: String, clazz: Class<T>): T {
        return Retrofit.Builder().baseUrl(baseUrl).client(mClient)
            .addConverterFactory(GsonConverterFactory.create(com.google.gson.Gson()))
            .build().create(clazz)
    }

    /**
     * OkHttpClient客户端
     */
    private fun newClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)// 连接时间：30s超时
        readTimeout(10, TimeUnit.SECONDS)// 读取时间：10s超时
        writeTimeout(10, TimeUnit.SECONDS)// 写入时间：10s超时
    }.build()
}