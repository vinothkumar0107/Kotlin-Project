package com.example.location.kotlin.bottom_sheet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.location.kotlin.LoginResponse
import com.example.location.kotlin.login_api_kotlin.TaskCallback
import java.util.*
import kotlin.collections.HashMap

class BottomSheetViewModel(application: Application) : AndroidViewModel(application) {
    val bottomShettMutableData: MutableLiveData<BottomSheetClass> = MutableLiveData()
    val bottomSheetLiveData: LiveData<BottomSheetClass> get() = bottomShettMutableData

    val repository = BottomSheetRepository()

    fun bottomSheet(userId: String, slotDate: String) {
        var header: HashMap<String, String> = HashMap()
        header.put("Authkey", "tok_b4ae481f07d0bd86a88e8eb504f256bc")

        var body: HashMap<String, String> = HashMap()
        body.put("user_id", userId)
        body.put("slot_date", slotDate)

        repository.getBottomSheetData(header, body, object : TaskCallback<BottomSheetClass> {
            override fun onComplete(result: BottomSheetClass?) {
                bottomShettMutableData.postValue(result!!)
            }

            override fun onException(t: Throwable?) {
                TODO("Not yet implemented")
            }

        })

    }

}