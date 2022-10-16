package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.BaseBean
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.core.TokenLoader
import com.example.smartcity.net.Repository
import com.example.smartcity.net.params.UpdateUserBean
import com.example.smartcity.net.pojo.GetUserBean
import com.example.smartcity.net.pojo.UploadFileBean
import java.io.File

class GetUserModel: BaseViewModel() {
    var userData = MutableLiveData<GetUserBean>()
    var uploadFileResponseData = MutableLiveData<UploadFileBean>()
    var updateUserData = MutableLiveData<BaseBean>()

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

    fun uploadFile(file: File, token: String) = launch(
        {
            loadState.value = LoadState.Loading()
            uploadFileResponseData.value = Repository.uploadFile(file, token)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )

    fun updateUser(token: String, updateUserBean: UpdateUserBean) = launch(
        {
            loadState.value = LoadState.Loading()
            updateUserData.value = Repository.updateUser(token, updateUserBean)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
}