package com.example.smartcity

import com.example.smartcity.utils.SimpleEncryptUtil
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val str = "hello 你好 ⑤🚗"
        val encrypt = SimpleEncryptUtil.encrypt(str)
        println(encrypt)
        val decode = SimpleEncryptUtil.decode(encrypt)
        println(decode)
    }
}