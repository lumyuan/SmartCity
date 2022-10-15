package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.GetUserBean

class GetUserModel: BaseViewModel() {
    var userData = MutableLiveData<GetUserBean>()

    fun getUser() = launch(
        {
            loadState.value = LoadState.Loading()
            userData.value = Repository.getUser(TokenLoader.load()!!)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )

    fun getUser(token: String) = launch(
        {
            loadState.value = LoadState.Loading()
            userData.value = Repository.getUser(token)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
}