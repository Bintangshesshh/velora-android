package com.example.velora

import android.content.Context
import android.content.SharedPreferences

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("VeloraPrefs", Context.MODE_PRIVATE)

    // Menyimpan data akun saat pendaftaran di Register
    fun saveUser(username: String, gmail: String, password: String) {
        prefs.edit().apply {
            putString("username", username)
            putString("gmail", gmail)
            putString("password", password)
            apply()
        }
    }

    // Mengatur status session login (aktif / tidak)
    fun saveSession(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)
    fun getUsername(): String? = prefs.getString("username", "")
    fun getGmail(): String? = prefs.getString("gmail", "")
    fun getPassword(): String? = prefs.getString("password", "")

    // Menghapus session saat klik tombol Logout
    fun clearSession() {
        prefs.edit().putBoolean("is_logged_in", false).apply()
    }
}