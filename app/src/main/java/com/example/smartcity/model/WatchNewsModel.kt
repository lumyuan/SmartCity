package com.example.smartcity.model

import androidx.lifecycle.MutableLiveData
import com.example.smartcity.base.BaseViewModel
import com.example.smartcity.bean.BaseBean
import com.example.smartcity.bean.LoadState
import com.example.smartcity.common.launch
import com.example.smartcity.net.Repository
import com.example.smartcity.net.pojo.*

class WatchNewsModel: BaseViewModel() {
    val newsData = MutableLiveData<WatchNewsParentBean<WatchNewsBean>>()
    val commentData = MutableLiveData<ResponseListBean<CommentBean>>()
    val topNewsData = MutableLiveData<ResponseListBean<NewsBean>>()
    val postCommentData = MutableLiveData<BaseBean>()
    fun watchNews(id: Int) = launch(
        {
            loadState.value = LoadState.Loading("news")
            newsData.value = Repository.watchNews(id)
            loadState.value = LoadState.Success("news")
        }, {
            loadState.value = LoadState.Fail("news")
        }
    )

    fun watchComment(newsId: Int) = launch(
        {
            loadState.value = LoadState.Loading("comment")
            commentData.value = Repository.getCommentList(newsId)
            loadState.value = LoadState.Success("comment")
        }, {
            loadState.value = LoadState.Fail("comment")
        }
    )

    fun getTopNews() = launch(
        {
            loadState.value = LoadState.Loading("top")
            topNewsData.value = Repository.getTopNews("Y")
            loadState.value = LoadState.Success("top")
        }, {
            loadState.value = LoadState.Fail("top")
        }
    )

    fun postComment(token: String, postCommentBean: PostCommentBean) = launch(
        {
            loadState.value = LoadState.Loading("postComment")
            postCommentData.value = Repository.postComment(token, postCommentBean)
            loadState.value = LoadState.Success("postComment")
        }, {
            loadState.value = LoadState.Fail("postComment")
        }
    )
}