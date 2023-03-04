package com.example.location.kotlin.login_api_kotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.location.kotlin.LoginResponse
import java.util.*

class LoginViewModel(application: Application) : AndroidViewModel(application)
{
    val repository = Repository()

    val loginResult: MutableLiveData<LoginResponse> = MutableLiveData()

    val liveData: LiveData<LoginResponse> get() = loginResult


    fun loginUser(email: String, pass: String) {
        var bodyData: HashMap<String, String> = HashMap()
        bodyData.put("email", email)
        bodyData.put("password", pass)
        bodyData.put("gcm_id", "fdjasbfkjbdzascfjdzncfkvnzdmf;ldzasmf")
        bodyData.put("device_model", "Android")
        bodyData.put("device_os", "12")
        bodyData.put("reg_type", "normal")

        var headerData: HashMap<String, String> = HashMap()
        headerData.put("Authkey", "tok_b4ae481f07d0bd86a88e8eb504f256bc")

        repository.getLoginUser(headerData, bodyData, object : TaskCallback<LoginResponse> {
            override fun onException(t: Throwable?) {
            }
            override fun onComplete(result: LoginResponse?) {
                loginResult.postValue(result!!)
            }
        })
    }

}




