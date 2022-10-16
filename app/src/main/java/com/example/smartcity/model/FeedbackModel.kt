package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.BaseBean
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.params.FeedbackBean

class FeedbackModel: BaseViewModel() {
    val feedbackData = MutableLiveData<BaseBean>()

    fun postFeedback(token: String, feedbackBean: FeedbackBean) = launch(
        {
            loadState.value = LoadState.Loading()
            feedbackData.value = Repository.feedback(token, feedbackBean)
            loadState.value = LoadState.Success()
        }, {
            loadState.value = LoadState.Fail()
        }
    )
}