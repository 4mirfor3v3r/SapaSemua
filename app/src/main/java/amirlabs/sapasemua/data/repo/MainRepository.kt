package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.data.model.Quiz
import amirlabs.sapasemua.data.model.QuizResult
import amirlabs.sapasemua.data.model.SubModule
import amirlabs.sapasemua.data.model.User
import io.reactivex.rxjava3.core.Single
import java.io.File

interface MainRepository {
    fun login(user: Map<String, Any>): Single<BaseResponse<User>>
    fun register(user: Map<String, Any>): Single<BaseResponse<User>>
    fun getProfile(userId: String): Single<BaseResponse<User>>
    fun updateProfile(body: Map<String?, Any>, avatar: File?): Single<BaseResponse<User>>
    fun getAllModule(): Single<BaseResponse<List<Module>>>
    fun getOneModule(moduleId: String): Single<BaseResponse<Module>>
    fun getOneSubModule(submoduleId: String): Single<BaseResponse<SubModule>>
    fun createModule(id: String?, module: Map<String, Any>, image: File, submodule:List<Map<String, Any>>, video:List<File>): Single<BaseResponse<Module>>
    fun createQuiz(body: Map<String, Any>, attachment: File): Single<BaseResponse<Quiz>>
    fun getQuizByModule(moduleId: String): Single<BaseResponse<List<Quiz>>>
    fun getQuizQuestion(moduleId: String): Single<BaseResponse<List<Quiz>>>
    fun submitQuiz(body:Map<String, Any>): Single<BaseResponse<QuizResult>>
    fun getQuizResult(resultId: String): Single<BaseResponse<QuizResult>>
}