package com.example.smartcity.net

import com.example.smartcity.bean.BaseBean
import com.example.smartcity.net.params.*
import com.example.smartcity.net.pojo.ResponseListBean
import com.example.smartcity.net.pojo.*
import okhttp3.MultipartBody
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

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/prod-api/api/register")
    suspend fun register(@Body registerBean: RegisterBean): BaseBean

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/prod-api/api/login")
    suspend fun login(@Body loginBean: LoginBean): ResponseLoginBean

    @GET("/prod-api/api/common/user/getInfo")
    suspend fun getUser(@Header("Authorization") token: String): GetUserBean

    @Multipart
    @POST("/prod-api/common/upload")
    suspend fun uploadFile(@Header("Authorization") token: String, @Part file: MultipartBody.Part): UploadFileBean

    @PUT("/prod-api/api/common/user")
    suspend fun updateUser(@Header("Authorization") token: String, @Body updateUserBean: UpdateUserBean): BaseBean

    @GET("/prod-api/api/allorder/list")
    suspend fun getOrderList(@Header("Authorization") token: String): ResponseListBean<OrderBean>

    @PUT("/prod-api/api/common/user/resetPwd")
    suspend fun updatePassword(@Header("Authorization") token: String, @Body updatePasswordBean: UpdatePasswordBean): BaseBean

    @POST("/prod-api/api/common/feedback")
    suspend fun feedback(@Header("Authorization") token: String, @Body feedbackBean: FeedbackBean): BaseBean

    @GET("/prod-api/press/press/{id}")
    suspend fun watchNews(@Path("id") id: Int): WatchNewsParentBean<WatchNewsBean>

    @GET("/prod-api/press/comments/list")
    suspend fun getCommentList(@Query("newsId") newsId: Int): ResponseListBean<CommentBean>

    @GET("/prod-api/press/press/list")
    suspend fun getTopNews(@Query("top") isTop: String): ResponseListBean<NewsBean>

    @POST("/prod-api/press/pressComment")
    suspend fun postComment(@Header("Authorization") token: String, @Body postCommentBean: PostCommentBean): BaseBean
}