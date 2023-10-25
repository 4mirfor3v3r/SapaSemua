package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.api.service.MainService
import amirlabs.sapasemua.data.local.dao.VideoDao
import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.data.model.User
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainRepositoryImpl(private val mainDao: VideoDao, private val mainApi: MainService) :
    MainRepository {
    //    override fun addReport(report: HistoryReportModel) {
//        mainDao.insert(report.toReportEntity())
//    }
//
//    override fun addAllReport(report: List<HistoryReportModel>) {
//        report.forEach {
//            mainDao.insert(it.toReportEntity())
//        }
//    }
//
//    override fun getAllReportHistory(): List<HistoryReportModel> {
//        return mainDao.getAllNotification().map { entity -> entity.toHistoryReport() }
//    }
//
//    override fun getRoute(
//        origin: LatLng,
//        destination: LatLng,
//        avoidAreas: List<LatLng>
//    ): Single<RouteResponse> {
//        var avoidArea:String? = null
//        if(avoidAreas.isNotEmpty()) {
//            avoidAreas.forEach {
//                avoidArea =
//                    "${avoidArea}bbox:${it.longitude - 0.0004},${it.latitude + 0.0004},${it.longitude + 0.0004},${it.latitude - 0.0004}|"
//            }
//        }
//        return mainApi.getRoute(
//            origin = "${origin.latitude},${origin.longitude}",
//            destination = "${destination.latitude},${destination.longitude}",
//            avoidArea = avoidArea
//            )
//    }
    override fun login(form: Map<String, Any>): Single<BaseResponse<User>> {
        return mainApi.login(form)
    }

    override fun register(user: Map<String, Any>): Single<BaseResponse<User>> {
        return mainApi.register(user)
    }

    override fun getAllModule(): Single<BaseResponse<List<Module>>> {
        return mainApi.getAllModule()
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
}