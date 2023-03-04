package com.example.location.kotlin.recycler_view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.location.kotlin.login_api_kotlin.TaskCallback

class ListViewModel(application: Application) : AndroidViewModel(application)
{
    val listMutableData:MutableLiveData<ListResponse>   = MutableLiveData()
    val listLiveData :LiveData<ListResponse> get() = listMutableData
    val listRepository = ListRepository()

    fun listOfData(user_id:String,page_number:String){

        var header:HashMap<String,String> = HashMap()
        header.put("Authkey","tok_b4ae481f07d0bd86a88e8eb504f256bc")

        var body:HashMap<String,String> = HashMap()
        body.put("user_id",user_id)
        body.put("page_number",page_number)

        listRepository.getListData(header,body,object :TaskCallback<ListResponse>{
            override fun onException(t: Throwable?) {
            }
            override fun onComplete(result: ListResponse?) {
                listMutableData.postValue(result!!)
            }
        })
    }
}
