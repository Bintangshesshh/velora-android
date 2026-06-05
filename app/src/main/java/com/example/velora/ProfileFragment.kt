package com.example.velora

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        authManager = AuthManager(requireContext())

        // Inisialisasi View dari Layout XML
        val tvProfileUsername = view.findViewById<TextView>(R.id.tvProfileUsername)
        val tvProfileEmail = view.findViewById<TextView>(R.id.tvProfileEmail)
        val etEditUsername = view.findViewById<EditText>(R.id.etEditUsername)
        val btnSaveProfile = view.findViewById<Button>(R.id.btnSaveProfile)
        val btnLogoutProfile = view.findViewById<Button>(R.id.btnLogoutProfile)

        // 1. Ambil data registrasi dari SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("UserRegPref", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("registered_username", "User Velora")
        val savedGmail = sharedPref.getString("registered_gmail", "core@velora.io")

        // Tampilkan data ke layar profil
        tvProfileUsername.text = savedUsername
        tvProfileEmail.text = savedGmail
        etEditUsername.setText(savedUsername)

        // 2. Logika Tombol Simpan Perubahan Username
        btnSaveProfile.setOnClickListener {
            val usernameBaru = etEditUsername.text.toString().trim()

            if (usernameBaru.isNotEmpty()) {
                // Simpan perubahan username ke SharedPreferences
                with(sharedPref.edit()) {
                    putString("registered_username", usernameBaru)
                    apply()
                }

                // Update tampilan teks di layar secara realtime
                tvProfileUsername.text = usernameBaru
                Toast.makeText(requireContext(), "Username berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Logika Tombol Logout (Sudah Diperbaiki)
        btnLogoutProfile.setOnClickListener {
            // Hapus status session login
            authManager.saveSession(false)
            Toast.makeText(requireContext(), "Disconnecting node... Logout Berhasil", Toast.LENGTH_SHORT).show()

            // Sembunyikan Floating Navbar di MainActivity saat kembali ke halaman Login
            val bottomNav = activity?.findViewById<LinearLayout>(R.id.floating_navbar_container)
            bottomNav?.visibility = View.GONE

            // Alihkan kembali ke LoginFragment
            (activity as? MainActivity)?.switchFragment(LoginFragment())
        }

        return view
    }
}