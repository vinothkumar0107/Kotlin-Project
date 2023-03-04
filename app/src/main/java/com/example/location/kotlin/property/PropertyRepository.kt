package com.example.location.kotlin.property

import com.example.location.kotlin.LoginEndpoints
import com.example.location.kotlin.login_api_kotlin.Result
import com.example.location.kotlin.login_api_kotlin.TaskCallback
import com.example.location.kotlin.login_api_kotlin.awaitResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyRepository {

    val backgroundScope = CoroutineScope(Dispatchers.IO)
    val foregroudScope = CoroutineScope(Dispatchers.Main)
    var loginEndpoints = LoginEndpoints.getApi()

    fun getProperty(
        header: HashMap<String,String>,
        body: HashMap<String,String>,
        taskCallback: TaskCallback<PropertyClass>)
    {
        backgroundScope.launch {
           when(val result:Result<PropertyClass> = loginEndpoints?.property(header,body)!!.awaitResult()){
               is Result.Ok->foregroudScope.launch {
                   taskCallback.onComplete(result.value)
               }
               is Result.Error->foregroudScope.launch {
                   taskCallback.onException(result.exception)
               }
               is Result.Exception->foregroudScope.launch {
                   taskCallback.onException(result.exception)
               }
           }
        }
    }
}