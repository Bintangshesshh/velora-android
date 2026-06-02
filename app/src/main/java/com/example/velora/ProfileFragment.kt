package com.example.velora

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private lateinit var authManager: AuthManager
    private var selectedAvatarUri: Uri? = null

    // Komponen UI Display
    private lateinit var ivAvatar: ImageView
    private lateinit var tvDisplayProfileName: TextView
    private lateinit var tvDisplayUsername: TextView
    private lateinit var tvDisplayBio: TextView
    private lateinit var btnEditProfile: Button

    // Komponen UI Edit Form
    private lateinit var etEditName: EditText
    private lateinit var etEditBio: EditText
    private lateinit var btnSaveProfile: Button
    private lateinit var btnCancelProfile: Button
    private lateinit var tvAvatarHint: TextView

    // Tombol Logout
    private lateinit var btnLogoutProfile: Button

    // Registrasi Picker Gambar Galeri
    private val pickAvatarLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedAvatarUri = uri
            Glide.with(this)
                .load(uri)
                .circleCrop()
                .into(ivAvatar)
            Toast.makeText(requireContext(), "Avatar preview loaded!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        authManager = AuthManager(requireContext())

        // Inisialisasi komponen view
        ivAvatar = view.findViewById(R.id.ivProfileAvatar)
        tvDisplayProfileName = view.findViewById(R.id.tvDisplayProfileName)
        tvDisplayUsername = view.findViewById(R.id.tvDisplayUsername)
        tvDisplayBio = view.findViewById(R.id.tvDisplayBio)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)

        etEditName = view.findViewById(R.id.etEditProfileName)
        etEditBio = view.findViewById(R.id.etEditBio)
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile)
        btnCancelProfile = view.findViewById(R.id.btnCancelProfile)
        tvAvatarHint = view.findViewById(R.id.tvAvatarHint)
        btnLogoutProfile = view.findViewById(R.id.btnLogoutProfile)

        loadProfileData()

        // Klik ganti avatar (hanya berfungsi di mode edit)
        ivAvatar.setOnClickListener {
            if (etEditName.visibility == View.VISIBLE) {
                pickAvatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }

        btnEditProfile.setOnClickListener { toggleEditMode(true) }

        btnCancelProfile.setOnClickListener {
            toggleEditMode(false)
            selectedAvatarUri = null
            loadProfileData()
        }

        btnSaveProfile.setOnClickListener {
            val namaBaru = etEditName.text.toString().trim()
            val bioBaru = etEditBio.text.toString().trim()

            if (namaBaru.isNotEmpty()) {
                val sharedPref = requireContext().getSharedPreferences("VeloraPref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("saved_custom_name", namaBaru)
                    putString("saved_custom_bio", bioBaru)
                    selectedAvatarUri?.let { uri ->
                        putString("saved_avatar_uri", uri.toString())
                    }
                    apply()
                }

                Toast.makeText(requireContext(), "Profile node synchronized!", Toast.LENGTH_SHORT).show()
                loadProfileData()
                toggleEditMode(false)
            } else {
                Toast.makeText(requireContext(), "Profile Name cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        // ================= SOLUSI ERROR LOGOUT FIX =================
        btnLogoutProfile.setOnClickListener {
            // Kita bypass fungsi authManager.logout() yang error.
            // Langsung bersihkan file SharedPreferences bawaan login aplikasi secara paksa.
            val loginPref = requireContext().getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
            loginPref.edit().clear().apply()

            // Bersihkan juga database profile kustom lokal kita
            val profilePref = requireContext().getSharedPreferences("VeloraPref", Context.MODE_PRIVATE)
            profilePref.edit().clear().apply()

            Toast.makeText(requireContext(), "Disconnected from Velora Core.", Toast.LENGTH_SHORT).show()

            // Tendang balik ke gerbang halaman LoginFragment
            (activity as? MainActivity)?.switchFragment(LoginFragment())
        }

        return view
    }

    private fun loadProfileData() {
        val sharedPref = requireContext().getSharedPreferences("VeloraPref", Context.MODE_PRIVATE)
        val defaultName = authManager.getUsername() ?: "Benediktus"
        val currentName = sharedPref.getString("saved_custom_name", defaultName)
        val currentBio = sharedPref.getString("saved_custom_bio", "Cyberpunk developer & ecosystem administrator.")
        val savedAvatarString = sharedPref.getString("saved_avatar_uri", null)
        val currentUsername = "ben_dev"

        tvDisplayProfileName.text = currentName
        tvDisplayUsername.text = "@$currentUsername"
        tvDisplayBio.text = currentBio

        etEditName.setText(currentName)
        etEditBio.setText(currentBio)

        val avatarTarget: Any = if (savedAvatarString != null) {
            Uri.parse(savedAvatarString)
        } else {
            "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?q=80&w=300"
        }

        Glide.with(this)
            .load(avatarTarget)
            .circleCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(ivAvatar)
    }

    private fun toggleEditMode(isEditing: Boolean) {
        if (isEditing) {
            tvDisplayProfileName.visibility = View.GONE
            tvDisplayBio.visibility = View.GONE
            btnEditProfile.visibility = View.GONE
            btnLogoutProfile.visibility = View.GONE

            etEditName.visibility = View.VISIBLE
            etEditBio.visibility = View.VISIBLE
            btnSaveProfile.visibility = View.VISIBLE
            btnCancelProfile.visibility = View.VISIBLE
            tvAvatarHint.visibility = View.VISIBLE
        } else {
            tvDisplayProfileName.visibility = View.VISIBLE
            tvDisplayBio.visibility = View.VISIBLE
            btnEditProfile.visibility = View.VISIBLE
            btnLogoutProfile.visibility = View.VISIBLE

            etEditName.visibility = View.GONE
            etEditBio.visibility = View.GONE
            btnSaveProfile.visibility = View.GONE
            btnCancelProfile.visibility = View.GONE
            tvAvatarHint.visibility = View.GONE
        }
    }
}
