package com.example.smartcity.net.pojo

import com.example.smartcity.bean.BaseBean

/**
 * 首页轮播图实体类
 */
class NavigationPictureListBean: BaseBean() {
    var rows: ArrayList<NavigationPictureBean>? = null
    var total: String? = null
    override fun toString(): String {
        return "NavigationPictureListBean(rows=$rows, total=$total)"
    }

}