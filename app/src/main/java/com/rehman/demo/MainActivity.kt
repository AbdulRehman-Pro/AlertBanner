package com.rehman.demo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rehman.view.AlertBanner

class MainActivity : AppCompatActivity() {

    private lateinit var alertBannerTop: AlertBanner
    private lateinit var alertBannerBottom: AlertBanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ), navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        alertBannerTop = findViewById(R.id.alertBanner)
        alertBannerBottom = findViewById(R.id.alertBanner2)


        alertBannerTop.showBanner("Oops! Something went wrong.", AlertBanner.LENGTH_SHORT)

        Handler(Looper.getMainLooper()).postDelayed({
            alertBannerTop.showBanner("No Internet Connection.", AlertBanner.LENGTH_LONG) {
                Toast.makeText(this, "Auto Banner dismissed.", Toast.LENGTH_SHORT).show()

            }
        }, 5000)


        alertBannerBottom.showBanner(
            "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish.",
            AlertBanner.LENGTH_INDEFINITE
        ) {
            Toast.makeText(this, "Clicked Banner dismissed.", Toast.LENGTH_SHORT).show()
        }


    }
}