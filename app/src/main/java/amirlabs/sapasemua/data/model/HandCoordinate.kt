package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class HandCoordinate(
    @SerializedName("x")
    var x: Float = 0f,
    @SerializedName("y")
    var y: Float = 0f,
    @SerializedName("z")
    var z: Float = 0f
)