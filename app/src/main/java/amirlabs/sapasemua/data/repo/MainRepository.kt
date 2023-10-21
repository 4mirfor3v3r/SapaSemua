package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.User
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun login(user: Map<String, Any>):Single<BaseResponse<User>>
    fun register(user: Map<String, Any>):Single<BaseResponse<User>>
}