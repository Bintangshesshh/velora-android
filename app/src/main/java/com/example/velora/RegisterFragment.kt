package com.example.velora

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val etUsername = view.findViewById<EditText>(R.id.etRegisterUsername)
        val etGmail = view.findViewById<EditText>(R.id.etRegisterGmail)
        val etPassword = view.findViewById<EditText>(R.id.etRegisterPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etRegisterConfirmPassword)
        val btnRegisterSubmit = view.findViewById<Button>(R.id.btnRegisterSubmit)
        val btnToLogin = view.findViewById<Button>(R.id.btnToLogin)

        // Kembali ke halaman Login
        btnToLogin.setOnClickListener {
            (activity as? MainActivity)?.switchFragment(LoginFragment())
        }

        btnRegisterSubmit.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val gmail = etGmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // 1. Validasi Kolom Kosong
            if (username.isEmpty() || gmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Validasi Kecocokan Password
            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Password dan Ulangi Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Jika Semua Valid, Simpan ke SharedPreferences
            val sharedPref = requireContext().getSharedPreferences("UserRegPref", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("registered_username", username)
                putString("registered_gmail", gmail)
                putString("registered_password", password)
                apply()
            }

            Toast.makeText(requireContext(), "Registration successful! Welcome $username", Toast.LENGTH_SHORT).show()

            // Lempar kembali ke LoginFragment setelah sukses mendaftar
            (activity as? MainActivity)?.switchFragment(LoginFragment())
        }

        return view
    }
}