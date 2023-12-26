package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.Forum
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.data.model.Quiz
import amirlabs.sapasemua.data.model.QuizResult
import amirlabs.sapasemua.data.model.SubModule
import amirlabs.sapasemua.data.model.Subscribe
import amirlabs.sapasemua.data.model.User
import com.tinder.scarlet.WebSocket
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.io.File

interface MainRepository {
    fun login(user: Map<String, Any>): Single<BaseResponse<User>>
    fun register(user: Map<String, Any>): Single<BaseResponse<User>>
    fun getProfile(userId: String): Single<BaseResponse<User>>
    fun updateProfile(body: Map<String?, Any>, avatar: File?): Single<BaseResponse<User>>

    fun getAllModule(): Single<BaseResponse<List<Module>>>
    fun getOneModule(moduleId: String): Single<BaseResponse<Module>>
    fun getLessons(moduleId: String): Single<BaseResponse<List<SubModule>>>
    fun getOneSubModule(submoduleId: String): Single<BaseResponse<SubModule>>
    fun createSubmodule(moduleId: String, body: Map<String, Any>, video: File): Single<BaseResponse<SubModule>>
    fun editSubmodule(submoduleId: String, body: Map<String, Any>, video: File?): Single<BaseResponse<SubModule>>
    fun deleteSubmodule(submoduleId: String): Single<BaseResponse<SubModule>>
    fun createModule(id: String?, module: Map<String, Any>, image: File, submodule:List<Map<String, Any>>, video:List<File>): Single<BaseResponse<Module>>
    fun editModule(moduleId: String, body: Map<String, Any>, image: File?): Single<BaseResponse<Module>>
    fun deleteModule(moduleId: String): Single<BaseResponse<Module>>

    fun createQuiz(body: Map<String, Any>, attachment: File): Single<BaseResponse<Quiz>>
    fun getOneQuiz(quizId: String): Single<BaseResponse<Quiz>>
    fun editQuiz(quizId: String, body: Map<String, Any>, attachment: File?): Single<BaseResponse<Quiz>>
    fun deleteQuiz(quizId: String): Single<BaseResponse<Quiz>>
    fun getQuizByModule(moduleId: String): Single<BaseResponse<List<Quiz>>>
    fun getQuizQuestion(moduleId: String): Single<BaseResponse<List<Quiz>>>
    fun submitQuiz(body:Map<String, Any>): Single<BaseResponse<QuizResult>>
    fun getQuizResult(resultId: String): Single<BaseResponse<QuizResult>>
    fun getAllQuizResult(userId:String): Single<BaseResponse<List<QuizResult>>>

    fun createForum(body: Map<String, Any>): Single<BaseResponse<Forum>>
    fun getForum(page:Int, pageSize:Int): Single<BaseResponse<List<Forum>>>
    fun getForumDetail(forumId:String): Single<BaseResponse<Forum>>
    fun addComment(forumId:String, body: Map<String, Any>): Single<BaseResponse<Forum>>

//    fun sendCoordinates(body: Subscribe)
//    fun getTranslateResult(): Flowable<String>
//    fun observeSocketConnection(): Flowable<WebSocket.Event>
}