package com.example.smartcity.net.pojo

class NewsBean {
    var commentNum: Int? = null
    var content: String? = null
    var cover: String? = null
    var hot: String? = null
    var id: Int? = null
    var likeNum: Int? = null
    var publishDate: String? = null
    var readNum: Int? = null
    var subTitle: String? = null
    var title: String? = null
    override fun toString(): String {
        return "NewsBean(commentNum=$commentNum, content=$content, cover=$cover, hot=$hot, id=$id, likeNum=$likeNum, publishDate=$publishDate, readNum=$readNum, subTitle=$subTitle, title=$title)"
    }
}