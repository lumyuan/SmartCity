package com.example.smartcity.utils

object SimpleEncryptUtil {

    private const val iv = 12138

    fun encrypt(content: String): String {
        val stringBuilder = StringBuilder()
        for(ch in content.toCharArray()){
            stringBuilder.append((ch + iv))
        }
        return stringBuilder.toString()
    }

    fun decode(content: String): String {
        val stringBuilder = StringBuilder()
        for(ch in content.toCharArray()){
            stringBuilder.append(ch - iv)
        }
        return stringBuilder.toString()
    }

}