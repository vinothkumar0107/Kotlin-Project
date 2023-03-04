package com.example.location.kotlin


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")var status:String,
    @SerializedName("response")var response:String,
    @SerializedName("is_alive_other") var aliveOther:String,
    @SerializedName("user_id") var userId:String,
    @SerializedName("user_type") var userType:String,
    @SerializedName("user_role") var userRole:String,
    @SerializedName("is_tour_enabled") var tourEnabled:String,
    @SerializedName("driver_name") var driverName:String,
    @SerializedName("sec_key") var secKey:String,
    @SerializedName("email") var email:String,
    @SerializedName("lang_code") var langCode:String,
    @SerializedName("push_key") var pushKey:String,
    )


