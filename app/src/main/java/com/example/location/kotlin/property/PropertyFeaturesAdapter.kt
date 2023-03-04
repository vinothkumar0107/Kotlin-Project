package com.example.location.kotlin.property

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import com.example.location.R

class PropertyFeaturesAdapter(
    var propertyFeatures: ArrayList<PropertyFeatures>,
    var context:Context
    ) :
    RecyclerView.Adapter<PropertyFeaturesAdapter.MyPropertyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyFeaturesAdapter.MyPropertyViewHolder {
       return MyPropertyViewHolder(LayoutInflater.from(parent.context)
           .inflate(R.layout.property_feature_item,parent,false))
    }

    override fun onBindViewHolder(holder: PropertyFeaturesAdapter.MyPropertyViewHolder, position: Int) {
       holder.furniture.text = propertyFeatures.get(position).title



        if(propertyFeatures.get(position).name.equals("has_swimming_pool"))
        {
            holder.imgFurniture.setImageResource(R.drawable.apartment)

        }else if(propertyFeatures.get(position).name.equals("has_furnished"))
        {
            holder.imgFurniture.setImageResource(R.drawable.furniture)
        }else if(propertyFeatures.get(position).name.equals("has_basement"))
        {
            holder.imgFurniture.setImageResource(R.drawable.basement)
        }
        else if(propertyFeatures.get(position).name.equals("has_maid_room"))
        {
            holder.imgFurniture.setImageResource(R.drawable.apartment)
        }
        else if(propertyFeatures.get(position).name.equals("has_balcony"))
        {
            holder.imgFurniture.setImageResource(R.drawable.balcony)
        }
        else if(propertyFeatures.get(position).name.equals("has_driver_room"))
        {
            holder.imgFurniture.setImageResource(R.drawable.driver)
        }

    }

    override fun getItemCount(): Int {
       return propertyFeatures.size
    }

    class MyPropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var furniture:TextView
        var imgFurniture:ImageView

        init {
            furniture = itemView.findViewById(R.id.tv_furniture)
            imgFurniture = itemView.findViewById(R.id.img_furniture)
        }
    }
}
