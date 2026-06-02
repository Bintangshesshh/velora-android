package com.example.velora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Membuka layout fragment_dashboard
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Ambil data nama user aktif dari AuthManager untuk menyapa user
        val authManager = AuthManager(requireContext())
        val usernameAktif = authManager.getUsername() ?: "User"

        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcomeDashboard)
        if (tvWelcome != null) {
            tvWelcome.text = "Welcome Back, $usernameAktif!\nVelora System Operational."
        }

        return view
    }
}