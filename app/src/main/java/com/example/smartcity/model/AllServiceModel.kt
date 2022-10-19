package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.ResponseListBean
import com.example.smartcity.net.pojo.ServiceBean

class AllServiceModel: BaseViewModel() {
    val serviceData = MutableLiveData<ResponseListBean<ServiceBean>>()
    fun getServiceData(isRisRecommend: String) = launch(
        {
            loadState.value = LoadState.Loading()
            serviceData.value = Repository.getServiceList(isRisRecommend)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
    fun searchService(regex: String): ArrayList<ServiceBean>{
        serviceData.value?.let {
            val rows = it.rows!!
            val list = ArrayList<ServiceBean>()
            rows.forEach { serviceBean->
                if (serviceBean.serviceName!!.contains(regex)){
                    list.add(serviceBean)
                }
            }
            return list
        }
        return ArrayList()
    }
}