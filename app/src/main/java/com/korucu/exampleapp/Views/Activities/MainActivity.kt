package com.korucu.exampleapp.Views.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.korucu.exampleapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val changePage = Intent(this, HamburgerActivity::class.java)
//        startActivity(changePage)
    }
}