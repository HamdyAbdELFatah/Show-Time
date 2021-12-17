package com.hamdy.showtime.ui.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hamdy.showtime.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setFlags (WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    ,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //navView.setBackgroundColor(ContextCompat.getColor(applicationContext, android.R.color.transparent))

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_tv,R.id.navigation_favorite, R.id.navigation_profile
            )
        )*/
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}