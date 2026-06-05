package com.example.velora

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengambil layout activity_main asli milikmu
        val activityMainId = resources.getIdentifier("activity_main", "layout", packageName)
        if (activityMainId != 0) {
            setContentView(activityMainId)
        }

        // Jalankan fragment login sebagai halaman pertama
        switchFragment(LoginFragment())
        updateNavbarVisibility()

        // Ambil komponen navigasi bawah secara dinamis agar aman dari eror compiler
        setupDynamicNavigation()
    }

    fun updateNavbarVisibility() {
        val navBarId = resources.getIdentifier("bottomNavigation", "id", packageName)
        if (navBarId != 0) {
            findViewById<View>(navBarId)?.visibility = if (isLoggedIn) View.VISIBLE else View.GONE
        }
    }

    private fun setupDynamicNavigation() {
        val res = resources
        val pkg = packageName

        // Cari tombol navigasi secara fleksibel (mencari kecocokan nama ID di XML kamu)
        val btnDashboard = findViewById<View>(res.getIdentifier("btnNavDashboard", "id", pkg))
            ?: findViewById<View>(res.getIdentifier("nav_home", "id", pkg))

        val btnGallery = findViewById<View>(res.getIdentifier("btnNavGallery", "id", pkg))
            ?: findViewById<View>(res.getIdentifier("nav_gallery", "id", pkg))

        val btnUpload = findViewById<View>(res.getIdentifier("btnNavUpload", "id", pkg))
            ?: findViewById<View>(res.getIdentifier("nav_upload", "id", pkg))

        val btnProfile = findViewById<View>(res.getIdentifier("btnNavProfile", "id", pkg))
            ?: findViewById<View>(res.getIdentifier("nav_profile", "id", pkg))

        btnDashboard?.setOnClickListener { if (isLoggedIn) switchFragment(DashboardFragment()) }
        btnGallery?.setOnClickListener { if (isLoggedIn) switchFragment(GalleryFragment()) }
        btnUpload?.setOnClickListener { if (isLoggedIn) switchFragment(UploadFragment()) }
        btnProfile?.setOnClickListener { if (isLoggedIn) switchFragment(ProfileFragment()) }
    }

    fun switchFragment(fragment: Fragment) {
        val containerId = resources.getIdentifier("fragmentContainer", "id", packageName)
        if (containerId != 0) {
            supportFragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit()
        }
    }
}