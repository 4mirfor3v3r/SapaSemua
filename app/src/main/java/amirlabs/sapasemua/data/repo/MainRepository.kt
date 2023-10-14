package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.model.BaseResponse
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun login(user: Map<String, Any>):Single<BaseResponse<Any>>
}