package com.example.smartcity.net

import android.content.Context
import com.example.smartcity.bean.BaseBean
import com.example.smartcity.common.ApiException
import com.example.smartcity.net.pojo.NavigationPictureListBean

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
    suspend fun getNavigationPicture(type: String): NavigationPictureListBean {
        val pictureListBean = NetworkService.api.getNavigationPicture(type).let {
            preprocessData(it)
        }
        println("NavigationPictureListBean: $pictureListBean")
        return pictureListBean
    }
}