package com.example.location.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.location.R
import com.example.location.firebase.MySharedPerference
import com.example.location.kotlin.login_api_kotlin.KotlinLoginActivity

class KotlinSplashActivity : AppCompatActivity()
{
    var sharedPerference: MySharedPerference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_splash)

        println("-------> splash onCreate")

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        sharedPerference = MySharedPerference(this)
        Handler().postDelayed(
            {
            if(sharedPerference!!.getBoolean(MySharedPerference.login))
            {
                println("-------> splash KotlinDashboardActivity")
                startActivity(Intent(this, KotlinDashboardActivity::class.java))
            }
            else{
                println("-------> splash KotlinLoginActivity")
                startActivity(Intent(this, KotlinLoginActivity::class.java))
            }
            finish()
        }, 3000)

    }
}



