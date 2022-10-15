package com.example.smartcity.net.pojo

class ServiceBean {
    var id: Int? = null
    var imgUrl: String? = null
    var link: String? = null
    var isRecommend: String? = null
    var serviceDesc: String? = null
    var serviceName: String? = null
    override fun toString(): String {
        return "ServiceBean(id=$id, imgUrl=$imgUrl, link=$link, isRecommend=$isRecommend, serviceDesc=$serviceDesc, serviceName=$serviceName)"
    }
}