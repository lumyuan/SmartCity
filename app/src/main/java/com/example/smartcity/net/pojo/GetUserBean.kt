package com.example.smartcity.net.pojo

import com.example.smartcity.bean.BaseBean

class GetUserBean: BaseBean() {
    var user: UserBean? = null
    override fun toString(): String {
        return "GetUserBean(user=$user)"
    }
}