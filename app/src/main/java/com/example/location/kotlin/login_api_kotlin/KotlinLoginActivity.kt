package com.example.location.kotlin.login_api_kotlin

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.location.databinding.ActivityLoginBinding
import com.example.location.firebase.BottomActivity
import com.example.location.firebase.MySharedPerference
import com.example.location.kotlin.KotlinDashboardActivity
import com.example.location.kotlin.register_api_kotlin.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class KotlinLoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var sharedPreference: MySharedPerference? = null
    var value: Boolean? = true
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    //lateinit var context:Context


    var array = arrayListOf<Any>("abi", 2, "", 6.0, 1.0, "hi", 77L)


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.liveData.observe(this) {
            if (it?.status == "1") {
                Toast.makeText(this,it.response,Toast.LENGTH_SHORT).show()

               var name = it.driverName
               var email = it.email
                SessionManager.storeData(this,name,email)
                it.response.let {
                    navigateToDashboard()
                }
            }else{
                Toast.makeText(this,it.response,Toast.LENGTH_SHORT).show()
            }
        }

        auth = FirebaseAuth.getInstance()
        sharedPreference = MySharedPerference(this)

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener()
        {
            do_login()
            // sing_in()
        }

    }

    private fun do_login() {
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        viewModel.loginUser(email, password)

    }

    private fun navigateToDashboard() {
        val intent = Intent(this, BottomActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)

    }

    private fun sing_in() {
        var user_email: String? = ""
        var user_password: String? = ""

        user_email = binding.etEmailLogin.text.toString().trim()
        user_password = binding.etPasswordLogin.text.toString().trim()
        if (TextUtils.isEmpty(user_email))
        {
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(user_password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            return
        }
        auth?.signInWithEmailAndPassword(user_email, user_password)?.addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful) {
                    sharedPreference?.storeData(user_email, user_password)
                    sharedPreference?.putBoolean(value!!)
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, KotlinDashboardActivity::class.java))
                } else {
                    Toast.makeText(
                        this,
                        "Please enter valid email id and password",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })

    }

}