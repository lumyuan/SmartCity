package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.NavigationPictureBean
import com.example.smartcity.net.pojo.ResponseListBean

class NavigationViewModel : BaseViewModel() {
    val pictureList = MutableLiveData<ResponseListBean<NavigationPictureBean>>()

    fun getData() = launch({
        loadState.value = LoadState.Loading()
        pictureList.value = Repository.getNavigationPicture("2")
        loadState.value = LoadState.Success()
    }, {
            loadState.value = LoadState.Fail()
        })
}