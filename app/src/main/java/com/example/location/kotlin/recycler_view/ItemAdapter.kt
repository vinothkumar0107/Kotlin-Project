package com.example.location.kotlin.recycler_view
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.location.R
import com.example.location.kotlin.property.PropertyActivity


class ItemAdapter(var arrayList: ArrayList<ListData>, var context: Context,var onClick:(id:String)->Unit) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_list_view,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemAdapter.MyViewHolder, position: Int) {

        holder.homeName.text="${arrayList.get(position).homeType}"
        holder.price.text="${arrayList.get(position).price} /month"
        holder.bedrooms.text="${arrayList.get(position).bedrooms} bed"
        holder.bathrooms.text="${arrayList.get(position).bathrooms} bath"
        holder.car.text="${arrayList.get(position).carParkings} car"
        holder.location.text="${arrayList.get(position).locationName}"
        Glide.with(context).load("${arrayList.get(position).photo}").into(holder.homeImage)

        holder.image.setOnClickListener{
            var intent = Intent(context,PropertyActivity::class.java)
           intent.putExtra("","")
            context.startActivity(intent)
        }

        holder.constraintLayout.setOnClickListener{
            onClick(arrayList[position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         //var id:TextView
         var homeName:TextView
         var price:TextView
         var bedrooms:TextView
         var bathrooms:TextView
         var car:TextView
         var location:TextView
         var homeImage:ImageView
         var constraintLayout: ConstraintLayout
         var image:ImageView

        init{
           // id = itemView.findViewById(R.id.tv_id)
            homeName = itemView.findViewById(R.id.tv_home_type)
            price = itemView.findViewById(R.id.tv_price)
            bedrooms = itemView.findViewById(R.id.bed)
            bathrooms = itemView.findViewById(R.id.bath)
            car = itemView.findViewById(R.id.car)
            location = itemView.findViewById(R.id.tv_location)
            homeImage = itemView.findViewById(R.id.img_house)
            constraintLayout = itemView.findViewById(R.id.cstl)
            image= itemView.findViewById(R.id.img_house)
        }
    }
}
