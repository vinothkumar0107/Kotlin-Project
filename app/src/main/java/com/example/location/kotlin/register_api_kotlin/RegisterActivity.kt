package com.example.location.kotlin.register_api_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.location.databinding.ActivityRegisterBinding
import com.example.location.firebase.BottomActivity
import com.example.location.kotlin.KotlinDashboardActivity

class RegisterActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityRegisterBinding

    val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSignUp.setOnClickListener(){
            sign_up()
        }

        viewModel.registerLiveData.observe(this){
            if(it.status=="1"){
                Toast.makeText(this,it.response,Toast.LENGTH_SHORT).show()
                Log.d("TAG", "onCreate: "+it.status)
                it.response.let {
                    val intent = Intent(this, BottomActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this,it.response,Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun sign_up(){
        var user_name = binding.etFullName.text.toString()
        var user_email = binding.etEmail.text.toString()
        var user_mobile_number = binding.etMobileNumber.text.toString()
        var user_password = binding.etPassword.text.toString()
        viewModel.register_data(user_name,user_email,user_mobile_number,user_password)
    }
}