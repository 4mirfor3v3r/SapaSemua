package amirlabs.sapasemua.data.api.service

import amirlabs.sapasemua.data.model.BaseResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JvmSuppressWildcards
interface MainService {
    @POST("auth/login")
    fun login(
        @Body body:Map<String,Any>
    ):Single<BaseResponse<Any>>
}