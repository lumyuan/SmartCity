package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.BaseBean
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.params.LoginBean
import com.example.smartcity.net.params.RegisterBean
import com.example.smartcity.net.pojo.ResponseLoginBean

class LoginModel: BaseViewModel() {
    val register = MutableLiveData<BaseBean>()
    val token = MutableLiveData<ResponseLoginBean>()

    fun register(registerBean: RegisterBean) = launch(
        {
            loadState.value = LoadState.Loading()
            register.value = Repository.register(registerBean)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )

    fun login(loginBean: LoginBean) = launch(
        {
            loadState.value = LoadState.Loading()
            token.value = Repository.login(loginBean)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
}