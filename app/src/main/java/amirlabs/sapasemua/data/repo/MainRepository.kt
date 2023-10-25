package amirlabs.sapasemua.data.repo

import amirlabs.sapasemua.data.model.BaseResponse
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.data.model.User
import io.reactivex.rxjava3.core.Single
import java.io.File

interface MainRepository {
    fun login(user: Map<String, Any>): Single<BaseResponse<User>>
    fun register(user: Map<String, Any>): Single<BaseResponse<User>>
    fun getAllModule(): Single<BaseResponse<List<Module>>>
    fun createModule(id: String?, module: Map<String, Any>, image: File, submodule:List<Map<String, Any>>, video:List<File>): Single<BaseResponse<Module>>
}