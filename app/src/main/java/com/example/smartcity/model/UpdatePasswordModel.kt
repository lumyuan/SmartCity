package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.BaseBean
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.params.UpdatePasswordBean

class UpdatePasswordModel: BaseViewModel() {
    val updatePasswordData = MutableLiveData<BaseBean>()
    fun updatePassword(token: String, updatePasswordBean: UpdatePasswordBean) = launch(
        {
            loadState.value = LoadState.Loading()
            updatePasswordData.value = Repository.updatePassword(token, updatePasswordBean)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
}