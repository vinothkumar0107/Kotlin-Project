package com.example.location.kotlin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.location.R
import com.example.location.firebase.UserData
import com.example.location.kotlin.login_api_kotlin.KotlinLoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class KotlinRegisterActivity : AppCompatActivity()
{
    var fullName : TextInputEditText?=null
    var user_email : TextInputEditText?= null
    var user_password : TextInputEditText?=null
    var mobileNumber : TextInputEditText?=null
    var user_age : TextInputEditText?=null
    var signUp : Button?=null
    var signIn : TextView?=null
    var auth : FirebaseAuth?=null
    var databaseReference : DatabaseReference?=null
    var firebaseDatabase : FirebaseDatabase?=null
    var userData : UserData?=null
    var imgBack: ImageView?=null
    val emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_register)

        fullName = findViewById(R.id.et_full_name)
        user_email = findViewById(R.id.et_email)
        user_password = findViewById(R.id.et_password)
        mobileNumber = findViewById(R.id.et_mobile_number)
       // user_age = findViewById(R.id.et_age)
        imgBack = findViewById(R.id.img_back)
        signUp = findViewById(R.id.btn_sign_in)
        signIn = findViewById(R.id.tv_sign)

        auth = FirebaseAuth.getInstance()

        imgBack?.setOnClickListener{
            startActivity(Intent(this, KotlinLoginActivity::class.java))
        }

        signUp?.setOnClickListener{

            val name: String = fullName?.getText().toString().trim()
            val email: String = user_email?.getText().toString().trim()
            val password : String = user_password?.getText().toString().trim()
            val mobile_number : String = mobileNumber?.getText().toString().trim()
            val age : String = user_age?.getText().toString().trim()

            if(TextUtils.isEmpty(name)){
                fullName?.setError("Name is Requried")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(email)){
                user_email?.setError("Email is Required")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(password)){
                user_password?.setError("Password is required")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(mobile_number)){
                mobileNumber?.setError("Mobile Number is Required")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(age)){
                user_age?.setError("Age is Required")
                return@setOnClickListener
            }
            userData = UserData(name, email, password, mobile_number, age)
            user_registration(email,password)

        }

        signIn?.setOnClickListener {
           startActivity(Intent(this, KotlinLoginActivity::class.java))
        }

    }

    private fun user_registration(email: String, password: String) {
        auth?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener(
            OnCompleteListener { data->
                val user = auth!!.currentUser
                if (user != null) {
                    store_register_data(user)
                    Toast.makeText(this,"Register Done",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, KotlinDashboardActivity::class.java))
                }else{
                    Toast.makeText(this,"Something wrong",Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun store_register_data(user:FirebaseUser)
    {
       if(userData!=null)
       {
           firebaseDatabase = FirebaseDatabase.getInstance()
           databaseReference = firebaseDatabase?.getReference()
           val uid:String = user.uid
           val databaseReference: DatabaseReference = databaseReference?.child("vinoth list")!!
           databaseReference.child(uid).setValue(userData)
       }
    }
}
