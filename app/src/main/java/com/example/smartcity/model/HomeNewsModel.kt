package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.NewsBean
import com.example.smartcity.net.pojo.ResponseListBean

class HomeNewsModel: BaseViewModel() {
    val newsData = MutableLiveData<ResponseListBean<NewsBean>>()

    fun getNews(type: String) = launch(
        {
            loadState.value = LoadState.Loading()
            newsData.value = Repository.getNewsClassifyList(type)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
}