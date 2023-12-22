package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.api.service.MainService
import amirlabs.sapasemua.data.api.service.WebSocketService
import amirlabs.sapasemua.data.local.dao.VideoDao
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainRepositoryImpl(private val mainDao: VideoDao, private val mainApi: MainService) :
    MainRepository {
    override fun login(form: Map<String, Any>): Single<BaseResponse<User>> {
        return mainApi.login(form)
    }

    override fun register(user: Map<String, Any>): Single<BaseResponse<User>> {
        return mainApi.register(user)
    }

    override fun getProfile(userId: String): Single<BaseResponse<User>> {
        return mainApi.getProfile(userId)
    }

    override fun updateProfile(body: Map<String?, Any>, avatar: File?): Single<BaseResponse<User>> {
        val sentBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (body.containsKey("id")) sentBody.addFormDataPart("id", body["id"].toString())
        if (body.containsKey("name")) sentBody.addFormDataPart("name", body["name"].toString())
        if (body.containsKey("email")) sentBody.addFormDataPart("email", body["email"].toString())
        if (body.containsKey("domicile")) sentBody.addFormDataPart("domicile", body["domicile"].toString())
        if (body.containsKey("bio")) sentBody.addFormDataPart("bio", body["bio"].toString())
        if (avatar != null){
            val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "avatar", avatar.name, avatar.asRequestBody("image/*".toMediaTypeOrNull())
            )
            sentBody.addPart(filePart)
        }
        return mainApi.updateProfile(sentBody.build())
    }

    override fun getAllModule(): Single<BaseResponse<List<Module>>> {
        return mainApi.getAllModule()
    }

    override fun getOneModule(moduleId: String): Single<BaseResponse<Module>> {
        return mainApi.getOneModule(moduleId)
    }

    override fun getLessons(moduleId: String): Single<BaseResponse<List<SubModule>>> {
        return mainApi.getLessons(moduleId)
    }

    override fun getOneSubModule(submoduleId: String): Single<BaseResponse<SubModule>> {
        return mainApi.getOneSubModule(submoduleId)
    }

    override fun createSubmodule(moduleId: String, body: Map<String, Any>, video: File): Single<BaseResponse<SubModule>> {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "video", video.name, video.asRequestBody("*/*".toMediaTypeOrNull())
        )
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("module", moduleId)
            .addFormDataPart("name", body["name"].toString())
            .addFormDataPart("duration", body["duration"].toString())
            .addPart(filePart)
        return mainApi.createSubmodule(body.build())
    }

    override fun editSubmodule(submoduleId: String, body: Map<String, Any>, video: File?): Single<BaseResponse<SubModule>> {
        val filePart: MultipartBody.Part? = video?.asRequestBody("*/*".toMediaTypeOrNull())?.let {
            MultipartBody.Part.createFormData(
                "video", video.name, it
            )
        }
        val sentBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (body.containsKey("name")) sentBody.addFormDataPart("name", body["name"].toString())
        if (body.containsKey("duration")) sentBody.addFormDataPart("duration", body["duration"].toString())
        if (filePart != null){
            sentBody.addPart(filePart)
        }
        return mainApi.editSubmodule(submoduleId, sentBody.build())
    }

    override fun deleteSubmodule(submoduleId: String): Single<BaseResponse<SubModule>> {
        return mainApi.deleteSubmodule(submoduleId)
    }

    override fun createModule(
        id: String?, module: Map<String, Any>, image: File, submodule:List<Map<String, Any>>, video:List<File>
    ): Single<BaseResponse<Module>> {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image", image.name, image.asRequestBody("image/*".toMediaTypeOrNull())
        )
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", module["name"].toString())
            .addFormDataPart("level", module["level"].toString())
            .addFormDataPart("description", module["description"].toString())
            .addFormDataPart("creator", id ?: "")
            .addPart(filePart)
        for (i in 0..submodule.lastIndex){
            body.addFormDataPart("submodule[${i}][name]", submodule[i]["name"].toString())
            body.addFormDataPart("submodule[${i}][duration]", submodule[i]["duration"].toString())
            val videoPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "modules", image.name, video[i].asRequestBody("*/*".toMediaTypeOrNull())
            )
            body.addPart(videoPart)
        }
        return mainApi.createModule(body.build())
    }

    override fun editModule(moduleId: String, body: Map<String, Any>, image: File?): Single<BaseResponse<Module>> {
        val filePart: MultipartBody.Part? = image?.asRequestBody("image/*".toMediaTypeOrNull())?.let {
            MultipartBody.Part.createFormData(
                "image", image.name, it
            )
        }
        val sentBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        if (body.containsKey("name")) sentBody.addFormDataPart("name", body["name"].toString())
        if (body.containsKey("level")) sentBody.addFormDataPart("level", body["level"].toString())
        if (body.containsKey("description")) sentBody.addFormDataPart("description", body["description"].toString())
        if (filePart != null){
            sentBody.addPart(filePart)
        }
        return mainApi.editModule(moduleId, sentBody.build())
    }

//    override fun editModule(
//        moduleId: String,
//        module: Map<String, Any>,
//        image: File,
//        submodule: List<Map<String, Any>>,
//        video: List<File>?
//    ): Single<BaseResponse<Module>> {
//        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
//            "image", image?.name, image.asRequestBody("image/*".toMediaTypeOrNull())
//        )
//        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart("name", module["name"].toString())
//            .addFormDataPart("level", module["level"].toString())
//            .addFormDataPart("description", module["description"].toString())
//            .addFormDataPart("creator", id ?: "")
//            .addPart(filePart)
//        for (i in 0..submodule.lastIndex){
//            body.addFormDataPart("submodule[${i}][name]", submodule[i]["name"].toString())
//            body.addFormDataPart("submodule[${i}][duration]", submodule[i]["duration"].toString())
//            val videoPart: MultipartBody.Part = MultipartBody.Part.createFormData(
//                "modules", image.name, video[i].asRequestBody("*/*".toMediaTypeOrNull())
//            )
//            body.addPart(videoPart)
//        }
//    }


    override fun deleteModule(moduleId: String): Single<BaseResponse<Module>> {
        return mainApi.deleteModule(moduleId)
    }

    override fun createQuiz(
        body: Map<String, Any>,
        attachment: File
    ): Single<BaseResponse<Quiz>> {
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "attachment", attachment.name, attachment.asRequestBody("*/*".toMediaTypeOrNull())
        )
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("module", body["module"].toString())
            .addFormDataPart("question", body["question"].toString())
            .addFormDataPart("answer", body["answer"].toString())
            .addFormDataPart("option1", body["option1"].toString())
            .addFormDataPart("option2", body["option2"].toString())
            .addFormDataPart("option3", body["option3"].toString())
            .addFormDataPart("option4", body["option4"].toString())
            .addPart(filePart)
        return mainApi.createQuiz(body.build())
    }

    override fun getQuizByModule(moduleId: String): Single<BaseResponse<List<Quiz>>> {
        return mainApi.getQuizByModule(moduleId)
    }

    override fun getQuizQuestion(moduleId: String): Single<BaseResponse<List<Quiz>>> {
        return mainApi.getQuizQuestion(moduleId)
    }

    override fun submitQuiz(
        body:Map<String, Any>
    ): Single<BaseResponse<QuizResult>> {
        return mainApi.submitQuiz(body)
    }

    override fun getQuizResult(resultId: String): Single<BaseResponse<QuizResult>> {
        return mainApi.getQuizResult(resultId)
    }

    override fun getAllQuizResult(userId: String): Single<BaseResponse<List<QuizResult>>> {
        return mainApi.getAllQuizResult(userId)
    }

    override fun createForum(body: Map<String, Any>): Single<BaseResponse<Forum>> {
        return mainApi.createForum(body)
    }

    override fun getForum(page: Int, pageSize: Int): Single<BaseResponse<List<Forum>>> {
        return mainApi.getForum(page, pageSize)
    }

    override fun getForumDetail(forumId: String): Single<BaseResponse<Forum>> {
        return mainApi.getForumDetail(forumId)
    }

    override fun addComment(
        forumId: String,
        body: Map<String, Any>
    ): Single<BaseResponse<Forum>> {
        return mainApi.addComment(forumId, body)
    }

//    override fun sendCoordinates(body: Subscribe) {
//        socketApi.sendCoordinates(body)
//    }
//
//    override fun getTranslateResult(): Flowable<String> {
//        return socketApi.observeCoordinates()
//    }
//
//    override fun observeSocketConnection(): Flowable<WebSocket.Event> {
//        return socketApi.observeWebSocketEvent()
//    }

}