package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("_id")
    val id: String,
    @SerializedName("forumId")
    val forumId: String?,
    @SerializedName("creator")
    val creator: User?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("replyTo")
    val replyTo: Comment?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
