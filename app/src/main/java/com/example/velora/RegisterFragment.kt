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

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val etUsername = view.findViewById<EditText>(R.id.etRegUsername)
        val etGmail = view.findViewById<EditText>(R.id.etRegGmail)
        val etPassword = view.findViewById<EditText>(R.id.etRegPassword)
        val etPasswordConfirm = view.findViewById<EditText>(R.id.etRegPasswordConfirm)
        val btnRegisterSubmit = view.findViewById<Button>(R.id.btnRegisterSubmit)
        val tvToLogin = view.findViewById<TextView>(R.id.tvToLogin)

        val authManager = AuthManager(requireContext())

        btnRegisterSubmit.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val gmail = etGmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val passwordConfirm = etPasswordConfirm.text.toString().trim()

            if (username.isEmpty() || gmail.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                Toast.makeText(requireContext(), "Password ke-ulang tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan akun ke database lokal SharedPreferences
            authManager.saveUser(username, gmail, password)
            Toast.makeText(requireContext(), "Registrasi Sukses! Silakan Login", Toast.LENGTH_SHORT).show()

            // Lempar balik ke halaman login agar user bisa masuk menggunakan akun barunya
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }

        tvToLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }

        return view
    }
}