package com.example.smartcity.net.pojo

import com.example.smartcity.bean.BaseBean

class UserBean {
    var avatar: String? = null
    var balance: Long? = null
    var email: String? = null
    var idCard: String? = null
    var nickName: String? = null
    var phonenumber: String? = null
    var score: Int? = null
    var sex: String? = null
    var userId: Int? = null
    var userName: String? = null
    override fun toString(): String {
        return "UserBean(avatar=$avatar, balance=$balance, email=$email, idCard=$idCard, nickName=$nickName, phonenumber=$phonenumber, score=$score, sex=$sex, userId=$userId, userName=$userName)"
    }
}