package com.example.smartcity.net.pojo

import com.example.smartcity.bean.BaseBean

class UploadFileBean: BaseBean() {
    var fileName: String? = null
    var url: String? = null
    override fun toString(): String {
        return "UploadFileBean(fileName=$fileName, url=$url, ${super.toString()})"
    }
}