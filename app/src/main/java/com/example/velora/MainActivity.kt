package com.example.velora

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigation)
        val authManager = AuthManager(this)

        // Setup Listener klik untuk menu navigasi bawah (ID diselaraskan dengan bottom_nav_menu.xml)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    switchFragment(DashboardFragment())
                    true
                }
                R.id.nav_gallery -> {
                    switchFragment(GalleryFragment())
                    true
                }
                R.id.nav_profile -> {
                    switchFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            // LOGIKA PROTECTED ROUTE CENTRAL
            if (authManager.isLoggedIn()) {
                // Jika sudah login, tampilkan navigasi & masuk ke halaman Dashboard default
                bottomNav.visibility = View.VISIBLE
                bottomNav.selectedItemId = R.id.nav_dashboard
            } else {
                // Jika belum login, sembunyikan navigasi dan paksa masuk ke gerbang Login
                bottomNav.visibility = View.GONE
                switchFragment(LoginFragment())
            }
        }
    }

    // Fungsi utilitas untuk mempermudah transaksi pergantian fragment
    fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        // Manajemen visibilitas Bottom Nav secara otomatis dan real-time
        if (fragment is LoginFragment || fragment is RegisterFragment) {
            bottomNav.visibility = View.GONE
        } else {
            bottomNav.visibility = View.VISIBLE
        }
    }
}