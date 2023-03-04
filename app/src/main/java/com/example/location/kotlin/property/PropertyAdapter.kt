package com.example.location.kotlin.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.location.R

class PropertyAdapter(
    var arrayList: ArrayList<Photos>,
    var context:Context)
    : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyAdapter.PropertyViewHolder {
        return PropertyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.property_item_adapeter,parent,false))
    }

    override fun onBindViewHolder(holder: PropertyAdapter.PropertyViewHolder, position: Int)
    {
      Glide.with(context).load(arrayList.get(position).path).into(holder.image)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image:ImageView

        init {
            image = itemView.findViewById(R.id.image_view)
        }

    }
}