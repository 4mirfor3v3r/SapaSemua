package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class Quiz(
    @SerializedName("_id") val id: String?,
    @SerializedName("question") val question: String?,
    @SerializedName("answer") val answer: String?,
    @SerializedName("attachment") val attachment: String?,
    @SerializedName("option1") val option1: String?,
    @SerializedName("option2") val option2: String?,
    @SerializedName("option3") val option3: String?,
    @SerializedName("option4") val option4: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?
)
