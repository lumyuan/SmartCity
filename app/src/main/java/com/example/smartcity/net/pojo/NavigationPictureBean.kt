package com.example.smartcity.net.pojo

class NavigationPictureBean {
    var advImg: String? = null
    var advTitle: String? = null
    var id: Int? = null
    var servModule: String? = null
    var sort: String? = null
    var targetId: Int? = null
    override fun toString(): String {
        return "NavigationPictureBean(advImg=$advImg, advTitle=$advTitle, id=$id, servModule=$servModule, sort=$sort, targetId=$targetId)"
    }

}