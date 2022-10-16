package com.example.smartcity.net.params

class UpdateUserBean {
    constructor()
    constructor(nickname: String){
        this.nickName = nickname
    }
    var avatar: String? = null
    var email: String? = null
    var idCard: String? = null
    var nickName: String? = null
    var phonenumber: String? = null
    var sex: String? = null
}