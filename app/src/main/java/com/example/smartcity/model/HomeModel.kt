package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.ResponseListBean
import com.example.smartcity.net.pojo.*

class HomeModel: BaseViewModel() {
    val bannerData = MutableLiveData<ResponseListBean<NavigationPictureBean>>()
    val serviceData = MutableLiveData<ResponseListBean<ServiceBean>>()
    val newsClassifyData = MutableLiveData<NewsClassifyListBean<NewsClassifyBean>>()
    val newsData = MutableLiveData<ResponseListBean<NewsBean>>()

    fun getBannerData() = launch(
        {
            loadState.value = LoadState.Loading()
            bannerData.value = Repository.getNavigationPicture("2")
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )

    fun getServiceData(isRisRecommend: String) = launch(
        {
            loadState.value = LoadState.Loading()
            serviceData.value = Repository.getServiceList(isRisRecommend)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )

    fun getNewsClassify() = launch({
        loadState.value = LoadState.Loading()
        newsClassifyData.value = Repository.getNewsClassify()
        loadState.value = LoadState.Success()
    }, {
        loadState.value = LoadState.Fail()
    })

    fun getNewsListData(type: String) = launch({
        loadState.value = LoadState.Loading()
        newsData.value = Repository.getNewsClassifyList(type)
        loadState.value = LoadState.Success()
    }, {
        loadState.value = LoadState.Fail()
    })
}