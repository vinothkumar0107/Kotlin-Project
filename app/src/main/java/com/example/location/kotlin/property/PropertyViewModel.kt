package com.example.location.kotlin.property

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.location.kotlin.login_api_kotlin.TaskCallback

class PropertyViewModel(application: Application) : AndroidViewModel(application)
{
    val propertyMutableLiveData:MutableLiveData<PropertyClass> = MutableLiveData()
    val propertyLiveData:LiveData<PropertyClass> get() = propertyMutableLiveData

    var repository = PropertyRepository()

    fun propertyData(property_id:String,user_id:String,type:String){

        var header:HashMap<String,String> = HashMap()
        header.put("Authkey","tok_b4ae481f07d0bd86a88e8eb504f256bc")

        var body:HashMap<String,String> = HashMap()
        body.put("property_id",property_id)
        body.put("user_id",user_id)
        body.put("type",type)

        repository.getProperty(header,body,object : TaskCallback<PropertyClass>{
            override fun onComplete(result: PropertyClass?) {
                propertyMutableLiveData.postValue(result!!)
            }

            override fun onException(t: Throwable?) {
                TODO("Not yet implemented")
            }

        })

    }
}