package com.example.smartcity.net

import com.example.smartcity.bean.BaseBean
import com.example.smartcity.model.LoginModel
import com.example.smartcity.net.params.LoginBean
import com.example.smartcity.net.params.RegisterBean
import com.example.smartcity.net.pojo.ResponseListBean
import com.example.smartcity.net.pojo.*
import retrofit2.http.*

/**
 * 网络服务接口(协程)
 * @JvmSuppressWildcards 用来注解类和方法，使得被标记元素的泛型参数不会被编译成通配符?
 */
@JvmSuppressWildcards
interface ApiService {

    @GET("/prod-api/api/rotation/list")
    suspend fun getNavigationPicture(@Query("type") type: String): ResponseListBean<NavigationPictureBean>

    @GET("/prod-api/press/press/list")
    suspend fun searchNews(@Query("title") title: String): ResponseListBean<NewsBean>

    @GET("/prod-api/api/service/list")
    suspend fun getServiceList(@Query("isRecommend") isRecommend: String): ResponseListBean<ServiceBean>

    @GET("/prod-api/press/category/list")
    suspend fun getNewsClassify(): NewsClassifyListBean<NewsClassifyBean>

    @GET("/prod-api/press/press/list")
    suspend fun getNewsClassifyList(@Query("type") type: String): ResponseListBean<NewsBean>

    @Headers(*["Content-Type: application/json;charset=UTF-8"])
    @POST("/prod-api/api/register")
    suspend fun register(@Body registerBean: RegisterBean): BaseBean

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/prod-api/api/login")
    suspend fun login(@Body loginBean: LoginBean): ResponseLoginBean

    @GET("/prod-api/api/common/user/getInfo")
    suspend fun getUser(@Header("Authorization") token: String): GetUserBean
}