package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class Forum(
    @SerializedName("_id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("attachment") val attachment: String?,
    @SerializedName("likes") val likes: Int,
    @SerializedName("comment") val comments: List<Comment> = emptyList(),
    @SerializedName("creator") val creator: User?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?
)
