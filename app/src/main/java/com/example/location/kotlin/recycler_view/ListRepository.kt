package com.example.location.kotlin.recycler_view

import com.example.location.kotlin.LoginEndpoints
import com.example.location.kotlin.login_api_kotlin.Result
import com.example.location.kotlin.login_api_kotlin.TaskCallback
import com.example.location.kotlin.login_api_kotlin.awaitResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.HashMap

class ListRepository {
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    private val foregroundScope = CoroutineScope(Dispatchers.Main)

    var loginEndpoints: LoginEndpoints = LoginEndpoints.getApi()!!

    fun getListData(header: HashMap<String, String>,
                    body:HashMap<String,String>,
                    taskCallback: TaskCallback<ListResponse>)
    {
        backgroundScope.launch {
            when (val result: Result<ListResponse> =
                loginEndpoints.favList(header,body).awaitResult()){
                is Result.Ok->foregroundScope.launch {
                    taskCallback.onComplete(result.value)
                }
                is Result.Exception->foregroundScope.launch {
                    taskCallback.onException(result.exception)
                }
                is Result.Error->foregroundScope.launch {
                    taskCallback.onException(result.exception)
                }
        }
        }
    }

}