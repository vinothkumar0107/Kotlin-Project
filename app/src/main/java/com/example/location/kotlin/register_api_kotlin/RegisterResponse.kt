package com.example.location.kotlin.register_api_kotlin

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("status") var status:String,
    @SerializedName("response") var response:String,
    @SerializedName("user_id") var userId:String,
    @SerializedName("user_name") var userName:String,
    @SerializedName("user_type") var userType:String,
    @SerializedName("user_role") var userRole:String,
    @SerializedName("email") var email:String,
    @SerializedName("mobile_number") var mobileNumber:String,
    @SerializedName("lang_code") var langCode:String,
    @SerializedName("currency") var currency:String
        )
