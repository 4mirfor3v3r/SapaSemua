package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<MODEL>(

    @SerializedName("msg")
    val message:String?,

    @SerializedName("response")
    val data:MODEL?,

    @SerializedName("access_token")
    val token:String?=null,

    @SerializedName("page")
    val page:Int?=null,

    @SerializedName("totalPage")
    val totalPage:Int?=null
)