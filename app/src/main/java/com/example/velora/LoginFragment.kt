package com.example.velora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val etGmail = view.findViewById<EditText>(R.id.etLoginGmail)
        val etPassword = view.findViewById<EditText>(R.id.etLoginPassword)
        val btnLoginSubmit = view.findViewById<Button>(R.id.btnLoginSubmit)
        val tvToRegister = view.findViewById<TextView>(R.id.tvToRegister)

        val authManager = AuthManager(requireContext())

        btnLoginSubmit.setOnClickListener {
            val gmail = etGmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (gmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val savedGmail = authManager.getGmail()
            val savedPassword = authManager.getPassword()

            // VALIDASI: Cocokkan input dengan data register di SharedPreferences
            if (gmail == savedGmail && password == savedPassword) {
                authManager.saveSession(true)
                Toast.makeText(requireContext(), "Login Berhasil! Mengakses Node...", Toast.LENGTH_SHORT).show()

                // Munculkan kembali Bottom Navigation Bar yang ada di MainActivity
                val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
                bottomNav?.visibility = View.VISIBLE

                // Pindahkan halaman ke GalleryFragment
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, GalleryFragment())
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Gmail atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        // Pindah ke halaman pendaftaran (Register) jika belum punya akun
        tvToRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RegisterFragment())
                .commit()
        }

        return view
    }
}