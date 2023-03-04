package com.example.location.kotlin.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.location.R

class SimilarPropertyAdapeter(var similarArrayList: ArrayList<SimilarProperty>, var context: Context) : RecyclerView.Adapter<SimilarPropertyAdapeter.MySimilarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarPropertyAdapeter.MySimilarViewHolder {
        return MySimilarViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.similar_property_item,parent,false))
    }

    override fun onBindViewHolder(holder: SimilarPropertyAdapeter.MySimilarViewHolder, position: Int) {
      Glide.with(context).load(similarArrayList.get(position).photo).into(holder.imgHouse)
        holder.homeType.text=similarArrayList.get(position).homeType
        holder.price.text ="${similarArrayList.get(position).price} /month"
        holder.location.text = similarArrayList.get(position).locationName

    }

    override fun getItemCount(): Int {
        return similarArrayList.size
    }

    class MySimilarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgHouse:ImageView
        var homeType:TextView
        var price:TextView
        var location:TextView

        init {
            imgHouse = itemView.findViewById(R.id.img_property)
            homeType = itemView.findViewById(R.id.tv_home)
            price = itemView.findViewById(R.id.tv_home_price)
            location = itemView.findViewById(R.id.tv_home_location)
        }
    }
}