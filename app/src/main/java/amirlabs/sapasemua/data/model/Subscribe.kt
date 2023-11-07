package amirlabs.sapasemua.data.model

import com.google.gson.annotations.SerializedName

data class Subscribe(
    @SerializedName("coordinates")
    val coordinates: List<HandCoordinate>
)