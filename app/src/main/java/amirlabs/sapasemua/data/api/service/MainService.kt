package amirlabs.sapasemua.data.api.service

import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.data.model.User
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JvmSuppressWildcards
interface MainService {
    @POST("auth/login")
    fun login(@Body body: Map<String, Any>): Single<BaseResponse<User>>

    @POST("auth/register")
    fun register(@Body body: Map<String, Any>): Single<BaseResponse<User>>

    @GET("module/get-all")
    fun getAllModule(): Single<BaseResponse<List<Module>>>

    @POST("module/create")
    fun createModule(@Body body: MultipartBody): Single<BaseResponse<Module>>
}