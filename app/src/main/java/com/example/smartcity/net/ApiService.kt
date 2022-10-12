package com.example.smartcity.net

import com.example.smartcity.net.pojo.NavigationPictureListBean
import retrofit2.http.*

/**
 * 网络服务接口(协程)
 * @author ssq
 * @JvmSuppressWildcards 用来注解类和方法，使得被标记元素的泛型参数不会被编译成通配符?
 */
@JvmSuppressWildcards
interface ApiService {

    @GET("/prod-api/api/rotation/list")
    suspend fun getNavigationPicture(@Query("type") type: String): NavigationPictureListBean

}