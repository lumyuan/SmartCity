package com.example.smartcity.net.pojo

import com.example.smartcity.bean.BaseBean

/**
 * 首页轮播图实体类
 */
class WatchNewsParentBean<T>: BaseBean() {
    var data: T? = null
    override fun toString(): String {
        return "NavigationPictureListBean(data=$data, total=$total)"
    }
}