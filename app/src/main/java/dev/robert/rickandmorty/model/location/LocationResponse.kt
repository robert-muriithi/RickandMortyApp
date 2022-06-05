package dev.robert.rickandmorty.model.location


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val locationsResults: List<LocationsResult>
)