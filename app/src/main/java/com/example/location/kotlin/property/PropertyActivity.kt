package com.example.location.kotlin.property

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.location.R
import com.example.location.firebase.BottomActivity

class   PropertyActivity : AppCompatActivity()
{
    lateinit var recyclerView:RecyclerView
    lateinit var propertyFeaturesRecyclerView:RecyclerView
    lateinit var similarPropertyRecyclerView:RecyclerView

    private val viewModel by viewModels<PropertyViewModel>()

    lateinit var homeType:TextView
    lateinit var price:TextView
    lateinit var location:TextView
    lateinit var bed:TextView
    lateinit var bath:TextView
    lateinit var car:TextView
    lateinit var floor:TextView
    lateinit var date:TextView
    lateinit var des:TextView
    lateinit var backButton:ImageButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        recyclerView = findViewById(R.id.property_recycler_view)
        //recyclerView
        homeType = findViewById(R.id.home_type)
        price = findViewById(R.id.tv_price)
        location = findViewById(R.id.tv_location)
        bed = findViewById(R.id.bed)
        bath = findViewById(R.id.bath)
        car = findViewById(R.id.car)
        floor = findViewById(R.id.tv_floor)
        date = findViewById(R.id.tv_date)
        des = findViewById(R.id.tv_des)
        backButton = findViewById(R.id.back_img_btn)

        backButton.setOnClickListener(){
            startActivity(Intent(this,BottomActivity::class.java))
        }

        propertyFeaturesRecyclerView = findViewById(R.id.property_features_recycler_view)

        similarPropertyRecyclerView = findViewById(R.id.similarPropertyRecyclerView)


        var propertyId = "1666274585-30fa-4134-aa6b-0a76cbd51fbf"
        var userId = "1663144227-548b-4c9e-9854-238f89102746"
        var type = "Details"
        viewModel.propertyData(propertyId,userId,type)

        viewModel.propertyLiveData.observe(this){
            it.response
            homeType.text=it.response?.homeType
            price.text = "${it.response?.price} /month"
            location.text = it.response?.locationName
            bed.text =" ${it.response?.bedrooms} bed"
            bath.text = "${it.response?.bathrooms} bath"
            car.text =" ${it.response?.carParkings} car"
            floor.text = "${it?.response?.floors} floor"
            date.text = "${it.response?.yearOfContruct}"
            des.text = "${it.response?.description}"

            Log.d("TAG", "PROPERTY_RESPONSE------------------->: "+it.response)
            val propertyAdapter = PropertyAdapter(it.response!!.photos,this)
            recyclerView.adapter = propertyAdapter

            val propertyFeaturesAdapter = PropertyFeaturesAdapter(it.response!!.propertyFeatures,this)
            propertyFeaturesRecyclerView.adapter=propertyFeaturesAdapter

            val similarPropertyAdapeter = SimilarPropertyAdapeter(it.response!!.similarProperty,this)
            similarPropertyRecyclerView.adapter = similarPropertyAdapeter



        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,BottomActivity::class.java))
    }
}