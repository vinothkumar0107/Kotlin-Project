package com.example.location.kotlin.login_api_kotlin

import com.example.location.kotlin.LoginEndpoints
import com.example.location.kotlin.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.DriverManager.println
import java.util.*

class Repository {
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    private val foregroundScope = CoroutineScope(Dispatchers.Main)

    var loginEndpoints: LoginEndpoints = LoginEndpoints.getApi()!!


    fun getLoginUser(
        headerData: HashMap<String, String>,
        bodyData: HashMap<String, String>,
        taskCallback: TaskCallback<LoginResponse>
    ) {
       // Log.d("TAG", "getLoginUser: "+headerData+bodyData)
        backgroundScope.launch {
            when (val result: Result<LoginResponse> =
                loginEndpoints.loginUser(headerData, bodyData).awaitResult()) {
                is Result.Ok -> foregroundScope.launch {
                    println("---process--- Calling completed repository")
                    taskCallback.onComplete(result.value)

                }
                is Result.Error -> foregroundScope.launch {
                    println("---process--- Calling error repository")
                    taskCallback.onException(result.exception)
                }
                is Result.Error -> foregroundScope.launch {
                    println("---process--- Calling exception repository")
                    taskCallback.onException(result.exception)
                }
            }
        }
    }
}