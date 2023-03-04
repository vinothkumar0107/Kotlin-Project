package com.example.location.kotlin.recycler_view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.location.R
import com.example.location.kotlin.KotlinDashboardActivity

class FavouriteListActivity : AppCompatActivity()
{
    lateinit var recyclerView:RecyclerView
    private val listViewModel by viewModels<ListViewModel>()
    var arrayList = ArrayList<ListData>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_list)

        recyclerView = findViewById(R.id.recycler_view)

        listData()

        listViewModel.listLiveData.observe(this){
            if(it?.status == "1"){
                if (it.response!=null)
                {
                    arrayList= it.response!!
                   // val itemAdapter = ItemAdapter(arrayList,this)
                   // recyclerView.adapter=itemAdapter
                }
            }else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun listData() {
        val user_id = "1663144227-548b-4c9e-9854-238f89102746"
        val page_number = "0"
        listViewModel.listOfData(user_id, page_number)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,KotlinDashboardActivity::class.java))
    }
}



