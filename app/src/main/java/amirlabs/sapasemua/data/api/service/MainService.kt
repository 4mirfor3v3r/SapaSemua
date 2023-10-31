package amirlabs.sapasemua.data.api.service

import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.data.model.Quiz
import amirlabs.sapasemua.data.model.QuizResult
import amirlabs.sapasemua.data.model.SubModule
import amirlabs.sapasemua.data.model.User
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

@JvmSuppressWildcards
interface MainService {
    @POST("auth/login")
    fun login(@Body body: Map<String, Any>): Single<BaseResponse<User>>

    @POST("auth/register")
    fun register(@Body body: Map<String, Any>): Single<BaseResponse<User>>

    @GET("module/get-all")
    fun getAllModule(): Single<BaseResponse<List<Module>>>

    @GET("module/{id}")
    fun getOneModule(@Path("id") moduleId:String): Single<BaseResponse<Module>>

    @GET("module/lesson/{id}")
    fun getOneSubModule(@Path("id") lessonId:String): Single<BaseResponse<SubModule>>

    @POST("module/create")
    fun createModule(@Body body: MultipartBody): Single<BaseResponse<Module>>

    @POST("module/quiz/create")
    fun createQuiz(@Body body: MultipartBody): Single<BaseResponse<Quiz>>

    @GET("module/{id}/quiz")
    fun getQuizByModule(@Path("id") moduleId:String): Single<BaseResponse<List<Quiz>>>

    @GET("module/{id}/quiz/list")
    fun getQuizQuestion(@Path("id") moduleId: String): Single<BaseResponse<List<Quiz>>>

    @POST("module/quiz/submit")
    fun submitQuiz(@Body body: Map<String, Any>): Single<BaseResponse<QuizResult>>

    @GET("module/quiz/result")
    fun getQuizResultByUser(@Body body: Map<String, Any>): Single<BaseResponse<List<QuizResult>>>

    @GET("module/quiz/result/{id}")
    fun getQuizResult(@Path("id") resultId: String): Single<BaseResponse<QuizResult>>
}