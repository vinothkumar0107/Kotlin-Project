package com.example.location.kotlin.recycler_view

import com.google.gson.annotations.SerializedName


data class ListResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("response") var response: ArrayList<ListData>? = null,

    )

data class ListData(
    @SerializedName("id") var id: String? = null,
    @SerializedName("d3_tour_status") var tourStatus: String? = null,
    @SerializedName("home_type") var homeType: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("bedrooms") var bedrooms: String? = null,
    @SerializedName("bathrooms") var bathrooms: String? = null,
    @SerializedName("car_parkings") var carParkings: String? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("location_name") var locationName: String? = null

)
