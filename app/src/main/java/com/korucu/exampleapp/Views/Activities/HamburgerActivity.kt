package com.korucu.exampleapp.Views.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import com.korucu.exampleapp.R
import com.korucu.exampleapp.Utils.CustomSharedPreferences
import com.korucu.exampleapp.databinding.ActivityHamburgerBinding

class HamburgerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHamburgerBinding

    private var customSharedPreferences = CustomSharedPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHamburgerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHamburger.toolbar)

//        binding.appBarHamburger.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_hamburger)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_addRecord, R.id.nav_list
            ), drawerLayout
//            setOf(
//                R.id.nav_addRecord, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_list
//            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        preapareFragment()
        onClicks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hamburger, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_hamburger)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logout(){
        customSharedPreferences.saveToken("null")

        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun onClicks(){
//        val logoutButton = findViewById<LinearLayout>(R.id.layoutLogOut)
//
//        logoutButton.setOnClickListener {
//            println("Deneme Sertac")
//        }

        binding.navView.getHeaderView(0).findViewById<LinearLayout>(R.id.layoutLogOut).setOnClickListener {
            logout()
        }



//        binding.navView.getHeaderView(0).setOnClickListener {
//            logout()
//        }
    }

    private fun preapareFragment(){
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.textViewEmail).text = customSharedPreferences.getEmail()
    }
}