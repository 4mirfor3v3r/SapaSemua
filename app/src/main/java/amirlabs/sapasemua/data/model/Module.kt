package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class Module(
    @SerializedName("_id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("level") val level: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("quiz") val quiz: List<Quiz>?,
    @SerializedName("creator") val creator: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?
)
