package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.api.service.MainService
import amirlabs.sapasemua.data.local.dao.VideoDao
import amirlabs.sapasemua.data.model.BaseResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody

class MainRepositoryImpl(private val mainDao: VideoDao, private val mainApi: MainService) : MainRepository {
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
    override fun login(form: Map<String, Any>): Single<BaseResponse<Any>> {
        return mainApi.login(form)
    }
}