package com.example.smartcity.model

import androidx.lifecycle.MediatorLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.OrderBean
import com.example.smartcity.net.pojo.ResponseListBean

class OrderModel: BaseViewModel() {
    val orderData = MediatorLiveData<ResponseListBean<OrderBean>>()
    fun getOrderList(token: String) = launch(
        {
            loadState.value = LoadState.Loading()
            orderData.value = Repository.getOrderList(token)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
}