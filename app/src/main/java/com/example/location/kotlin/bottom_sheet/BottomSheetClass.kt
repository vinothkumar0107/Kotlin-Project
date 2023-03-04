package com.example.location.kotlin.bottom_sheet

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName


data class BottomSheetClass (

    @SerializedName("status"   ) var status   : String?   = null,
    @SerializedName("response" ) var response : Response? = Response()

)
data class Response(

    @SerializedName("slot_date"    ) var slotDate    : String?                = null,
    @SerializedName("slot_timings" ) var slotTimings : ArrayList<SlotTimings> = arrayListOf()

)
data class SlotTimings(

    @SerializedName("time"         ) var time         : String? = null,
    @SerializedName("availability" ) var availability : String? = null

)