package com.example.location.kotlin

import com.example.location.kotlin.bottom_sheet.BottomSheetClass
import com.example.location.kotlin.login_api_kotlin.APIClient
import com.example.location.kotlin.login_api_kotlin.TaskCallback
import com.example.location.kotlin.property.PropertyClass
import com.example.location.kotlin.recycler_view.ListResponse
import com.example.location.kotlin.register_api_kotlin.RegisterResponse
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

interface LoginEndpoints {
    @FormUrlEncoded
    @POST("api/v1/user/login")
    fun loginUser(
        @HeaderMap header: HashMap<String, String>,
        @FieldMap map: HashMap<String, String>,
        ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/v1/user/register")
    fun registerUser(
        @HeaderMap header: HashMap<String, String>,
        @FieldMap bodyData: HashMap<String,String>
    ):Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/v1/property/favourite")
    fun favList(@HeaderMap header: HashMap<String, String>,
               @FieldMap bodyData: HashMap<String, String>

    ):Call<ListResponse>

    @FormUrlEncoded
    @POST("api/v1/account/get3dslots")
    fun bottom_sheet(@HeaderMap header: HashMap<String, String>,
                     @FieldMap bodyData: HashMap<String, String>
                     ):Call<BottomSheetClass>

    @FormUrlEncoded
    @POST("api/v1/property/get")
    fun property(
        @HeaderMap header: HashMap<String, String>,
        @FieldMap bodyData: HashMap<String, String>,
    ):Call<PropertyClass>



    companion object {
        fun getApi(): LoginEndpoints? {
            return APIClient.client?.create(LoginEndpoints::class.java)
        }
    }
}