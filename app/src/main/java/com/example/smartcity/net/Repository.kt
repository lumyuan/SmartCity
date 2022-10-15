package com.example.smartcity.net

import com.example.smartcity.bean.BaseBean
import com.example.smartcity.common.ApiException
import com.example.smartcity.net.params.LoginBean
import com.example.smartcity.net.params.RegisterBean
import com.example.smartcity.net.pojo.ResponseListBean
import com.example.smartcity.net.pojo.*
import com.example.smartcity.utils.LogUtils

/**
 * 接口资源
 * @author ssq
 */
object Repository {

    /**
     * 预处理数据(错误)
     */
    private fun <T : BaseBean> preprocessData(baseBean: T): T =
        if (baseBean.code == 200) {// 成功
            // 返回数据
            baseBean
        } else {// 失败
            // 抛出接口异常
            throw ApiException(baseBean.code!!, baseBean.msg!!)
        }

    /**
     * 获取导航页轮播图
     */
    suspend fun getNavigationPicture(type: String): ResponseListBean<NavigationPictureBean> {
        val pictureListBean = NetworkService.api.getNavigationPicture(type).let {
            preprocessData(it)
        }
        println("NavigationPictureListBean: $pictureListBean")
        return pictureListBean
    }

    /**
     * 搜索新闻资讯
     */
    suspend fun searchNews(title: String): ResponseListBean<NewsBean> {
        return NetworkService.api.searchNews(title).let {
            preprocessData(it)
        }
    }

    /**
     * 获取全部服务信息
     */
    suspend fun getServiceList(isRecommend: String): ResponseListBean<ServiceBean> {
        return NetworkService.api.getServiceList(isRecommend).let {
            preprocessData(it)
        }
    }

    /**
     * 获取新闻分类
     */
    suspend fun getNewsClassify(): NewsClassifyListBean<NewsClassifyBean> {
        return NetworkService.api.getNewsClassify().let {
            preprocessData(it)
        }
    }

    /**
     * 获取分类新闻资讯
     */
    suspend fun getNewsClassifyList(type: String): ResponseListBean<NewsBean> {
        return NetworkService.api.getNewsClassifyList(type).let {
            preprocessData(it)
        }
    }

    /**
     * 用户注册
     */
    suspend fun register(registerBean: RegisterBean): BaseBean {
        return NetworkService.api.register(registerBean).let {
            preprocessData(it)
        }
    }

    /**
     * 用户登录
     */
    suspend fun login(loginBean: LoginBean): ResponseLoginBean {
        return NetworkService.api.login(loginBean).let {
            preprocessData(it)
        }
    }

    /**
     * 获取用户信息
     */
    suspend fun getUser(token: String): GetUserBean{
        return NetworkService.api.getUser(token).let {
            LogUtils.print(it)
            preprocessData(it)
        }
    }
}