package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class SubModule(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("name")
    var name: String?= null,
    @SerializedName("video")
    var video: String?= null,
    @SerializedName("duration")
    var duration: Int?= null,
    @SerializedName("createdAt")
    val createdAt: String?= null,
    @SerializedName("updatedAt")
    val updatedAt: String?= null
)
