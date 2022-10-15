package com.example.smartcity.bean

open class BaseBean {
    var code: Int? = null
    var msg: String? = null
    var total: String? = null
    override fun toString(): String {
        return "BaseBean(code=$code, msg=$msg, total=$total)"
    }
}