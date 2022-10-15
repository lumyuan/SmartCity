package com.example.smartcity.net.pojo

import com.example.smartcity.bean.BaseBean

/**
 * 首页轮播图实体类
 */
class NewsClassifyListBean<T>: BaseBean() {
    var data: ArrayList<T>? = null
    override fun toString(): String {
        return "NavigationPictureListBean(rows=$data, total=$total)"
    }
}