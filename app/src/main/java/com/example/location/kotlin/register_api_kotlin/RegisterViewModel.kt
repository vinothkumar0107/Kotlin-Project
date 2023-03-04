package com.example.location.kotlin.register_api_kotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.location.kotlin.LoginResponse
import com.example.location.kotlin.login_api_kotlin.TaskCallback

class RegisterViewModel(application: Application) : AndroidViewModel(application)
{
    val registerRepository = RegisterRepository()
    val registerResult : MutableLiveData<RegisterResponse> = MutableLiveData()
    val registerLiveData : LiveData<RegisterResponse> get() = registerResult

    fun register_data(name:String,email:String,mobile:String,password:String){

        var bodyData:HashMap<String,String> = HashMap()

        bodyData.put("user_name",name)
        bodyData.put("email",email)
        bodyData.put("mobile_number",mobile)
        bodyData.put("password",password)
        bodyData.put("gcm_id","qwqwqewsfdsfde")
        bodyData.put("device_model","Samsung Galaxy M51")
        bodyData.put("device_os","12.0")
        bodyData.put("reg_type","fb")

        var headerData:HashMap<String,String> = HashMap()
        headerData.put("Authkey","tok_b4ae481f07d0bd86a88e8eb504f256bc")

        registerRepository.getRegisterUser(headerData,bodyData  ,object : TaskCallback<RegisterResponse>{
            override fun onException(t: Throwable?) {
            }
            override fun onComplete(result: RegisterResponse?) {
                registerResult.postValue(result!!)
            }
        })


    }


}