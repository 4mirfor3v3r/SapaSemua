package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id:String?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("email")
    val email:String?,
    @SerializedName("password")
    val password:String?,
    @SerializedName("avatar")
    val avatar:String?,
    @SerializedName("bio")
    val bio:String?,
    @SerializedName("createdAt")
    val createdAt:String?,
    @SerializedName("updatedAt")
    val updatedAt:String?
)
