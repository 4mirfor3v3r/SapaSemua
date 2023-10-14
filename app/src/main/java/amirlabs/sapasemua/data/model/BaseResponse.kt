package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<MODEL>(

    @SerializedName("message")
    val message:String?,

    @SerializedName("data")
    val data:MODEL?,

    @SerializedName("access_token")
    val token:String?=null
)