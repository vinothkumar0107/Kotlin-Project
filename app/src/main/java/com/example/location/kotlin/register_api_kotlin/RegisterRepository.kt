package com.example.location.kotlin.register_api_kotlin

import com.example.location.kotlin.LoginEndpoints
import com.example.location.kotlin.login_api_kotlin.Result
import com.example.location.kotlin.login_api_kotlin.TaskCallback
import com.example.location.kotlin.login_api_kotlin.awaitResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.DriverManager
import java.util.HashMap

class   RegisterRepository {
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    private val foregroundScope = CoroutineScope(Dispatchers.Main)

    var loginEndpoints: LoginEndpoints = LoginEndpoints.getApi()!!


    fun getRegisterUser(
        headerData: HashMap<String, String>,
        bodyData: HashMap<String, String>,
        taskCallback: TaskCallback<RegisterResponse>
    ) {
        backgroundScope.launch {
            when (val result: Result<RegisterResponse> =
                loginEndpoints.registerUser(headerData, bodyData).awaitResult()) {
                is Result.Ok -> foregroundScope.launch {
                    DriverManager.println("---process--- Calling completed repository")
                    taskCallback.onComplete(result.value)

                }
                is Result.Error -> foregroundScope.launch {
                    DriverManager.println("---process--- Calling error repository")
                    taskCallback.onException(result.exception)
                }
                is Result.Error -> foregroundScope.launch {
                    DriverManager.println("---process--- Calling exception repository")
                    taskCallback.onException(result.exception)
                }
            }
        }
    }
}