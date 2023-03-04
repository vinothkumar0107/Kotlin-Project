package com.example.location.kotlin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.location.R
import com.example.location.firebase.MySharedPerference
import com.example.location.firebase.UserData
import com.example.location.kotlin.bottom_sheet.BottomSheetAdapter
import com.example.location.kotlin.bottom_sheet.BottomSheetViewModel
import com.example.location.kotlin.bottom_sheet.SlotTimings
import com.example.location.kotlin.login_api_kotlin.KotlinLoginActivity
import com.example.location.kotlin.login_api_kotlin.SessionManager
import com.example.location.kotlin.recycler_view.FavouriteListActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class KotlinDashboardActivity : AppCompatActivity() {
    var name:TextView ?= null
    var email:TextView ?= null
    var mobile_number: TextView?=null
    var age: TextView?=null
    var viewfav:Button?=null
    var firebaseAuth: FirebaseAuth?=null
    var databaseReference: DatabaseReference?=null
    var firebaseDatabase:FirebaseDatabase?=null
    var firebaseUser: FirebaseUser?=null
     lateinit var logout:Button
     var context:Context ?=null
    var bottom_sheet:Button?=null
    var sharedPerference:MySharedPerference?=null
    private val viewModel by viewModels<BottomSheetViewModel>()

//     var recyclerView = RecyclerView()
    var arrayList = ArrayList<SlotTimings>()


    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_dashboard)

        name = findViewById(R.id.user_name)
        email = findViewById(R.id.user_email)
        mobile_number = findViewById(R.id.user_mobile_number)
        age = findViewById(R.id.user_age)
        logout = findViewById(R.id.btn_logout)
        viewfav = findViewById(R.id.btn_view_fav)
        bottom_sheet = findViewById(R.id.btn_bottom_sheet)



        val sessionManager = SessionManager
        sessionManager.getUserName(this)

        name?.text = sessionManager.getUserName(this)

        viewModel.bottomSheetLiveData.observe(this){
            if(it.status=="1"){
                if(it.response!=null){
                    arrayList = it.response!!.slotTimings
                    bottomSheet()
//                    val bottomSheetAdapter = BottomSheetAdapter(arrayList,this)
                }
            }
            else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }

        bottom_sheet?.setOnClickListener{

            var userId = "1663144227-548b-4c9e-9854-238f89102746"
            var slot_date = "22-10-2022"
            viewModel.bottomSheet(userId,slot_date)

        }

      logout.setOnClickListener{
          val alert = AlertDialog.Builder(this)
          alert.setMessage("Are you sure Logout").setPositiveButton("Logout", object :
              DialogInterface.OnClickListener {
              override fun onClick(p0: DialogInterface?, p1: Int) {
                  sharedPerference?.clearData()
                  startActivity(Intent(this@KotlinDashboardActivity,
                      KotlinLoginActivity::class.java))
              }
          }).setNegativeButton("Cancel", null)
          alert.create()
          alert.show()
      }



        viewfav?.setOnClickListener(){
            startActivity(Intent(this, FavouriteListActivity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference= FirebaseDatabase.getInstance().getReference()
        firebaseUser = firebaseAuth!!.currentUser

        if(databaseReference!=null){
            firebaseDatabase = FirebaseDatabase.getInstance()
            if(firebaseUser!=null){
                var user_id: String? = firebaseUser?.uid

                    databaseReference?.child("vinoth list")?.child(user_id!!)?.addValueEventListener(object :ValueEventListener{
                        @SuppressLint("SetTextI18n")
                        override fun onDataChange(snapshot: DataSnapshot)
                        {
                          if(snapshot.exists()){
                              var dataClass: UserData?= snapshot.getValue(UserData::class.java)
                              name?.text=dataClass?.fullName
                              email?.text = dataClass?.email
                              mobile_number?.text=dataClass?.mobile
                              age?.text=dataClass?.age
                              name?.setText("Name : ${dataClass}")
                              email?.setText("Email : ${dataClass?.email}")
                              mobile_number?.setText("Mobile : ${dataClass?.mobile}")
                              age?.setText("Age : ${dataClass?.age}")
                              age?.text=dataClass?.age
                              //age?.text.toString()
                          }
                        }
                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun bottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout,null)

        var botomRecycler = view.findViewById<RecyclerView>(R.id.bottom_sheet_recycler_view)

       // val bottomSheetAdapter = BottomSheetAdapter(arrayList,this)
       // botomRecycler.adapter = bottomSheetAdapter
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    finishAffinity()
    }
}