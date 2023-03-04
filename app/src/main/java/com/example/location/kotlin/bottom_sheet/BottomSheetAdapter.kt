package com.example.location.kotlin.bottom_sheet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.location.R
import com.example.location.kotlin.KotlinDashboardActivity
import com.example.location.kotlin.recycler_view.FavoriteFragment
import com.example.location.kotlin.recycler_view.ListData

class BottomSheetAdapter(var arrayList: ArrayList<SlotTimings>) :
    RecyclerView.Adapter<BottomSheetAdapter.MyBottomViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetAdapter.MyBottomViewHolder {
        return MyBottomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bottom_sheet_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BottomSheetAdapter.MyBottomViewHolder, position: Int) {
        holder.time.text = "Time : ${arrayList.get(position).time}"
        holder.availability.text = "Availability : ${arrayList.get(position).availability}"
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyBottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView
        var availability: TextView

        init {
            time = itemView.findViewById(R.id.tv_time)
            availability = itemView.findViewById(R.id.tv_availability)
        }

    }
}
