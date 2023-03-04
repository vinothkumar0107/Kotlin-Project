package com.example.location.kotlin.login_api_kotlin

import android.os.Message
import android.widget.Toast
import com.example.location.kotlin.LoginResponse
import com.example.location.kotlin.bottom_sheet.BottomSheetClass
import com.example.location.kotlin.recycler_view.ListResponse
import com.example.location.kotlin.register_api_kotlin.RegisterResponse

/*open class TaskCallback<T> {
    fun onComplete(value: String)

    fun onException(exception: Boolean) {

    }


    open fun onException(t: Throwable?)
    {

    }
    open fun onComplete(result: LoginResponse?)
    {

    }
    open fun onComplete(result: RegisterResponse?) {

    }

    open fun onComplete(result: ListResponse?) {

    }

    open fun onComplete(result: BottomSheetClass) {

    }

}*/


interface TaskCallback<T>
{
    fun onComplete(result: T?)

    fun onException(t: Throwable?)

}
