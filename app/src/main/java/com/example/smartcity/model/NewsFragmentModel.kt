package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.ResponseListBean
import com.example.smartcity.net.pojo.*

class NewsFragmentModel: BaseViewModel() {
    val newsClassifyData = MutableLiveData<NewsClassifyListBean<NewsClassifyBean>>()
    val newsData = MutableLiveData<ResponseListBean<NewsBean>>()

    fun getNewsClassify() = launch({
        loadState.value = LoadState.Loading()
        newsClassifyData.value = Repository.getNewsClassify()
        loadState.value = LoadState.Success()
    }, {
        loadState.value = LoadState.Fail()
    })

    fun getNewsListData() = launch({
        loadState.value = LoadState.Loading()
        newsData.value = Repository.searchNews("")
        loadState.value = LoadState.Success()
    }, {
        loadState.value = LoadState.Fail()
    })
}