package com.example.location.kotlin.bottom_sheet

import com.example.location.kotlin.LoginEndpoints
import com.example.location.kotlin.login_api_kotlin.Result
import com.example.location.kotlin.login_api_kotlin.TaskCallback
import com.example.location.kotlin.login_api_kotlin.awaitResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.HeaderMap
import java.sql.DriverManager

class BottomSheetRepository {
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    private val foregroundScope = CoroutineScope(Dispatchers.Main)

    var loginEndpoints: LoginEndpoints = LoginEndpoints.getApi()!!

    fun getBottomSheetData(
        header: HashMap<String,String>,
        body: HashMap<String,String>,
        taskCallback: TaskCallback<BottomSheetClass>
    )
    {
        backgroundScope.launch {
            when(val result: com.example.location.kotlin.login_api_kotlin.Result<BottomSheetClass> =
                loginEndpoints.bottom_sheet(header,body).awaitResult()){
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