package com.example.velora

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val ivAvatar = view.findViewById<ImageView>(R.id.ivDashAvatar)
        val tvName = view.findViewById<TextView>(R.id.tvDashProfileName)
        val tvTotalAssets = view.findViewById<TextView>(R.id.tvTotalAssets)
        val tvTotalLikes = view.findViewById<TextView>(R.id.tvTotalLikes)

        // Tarik data profil dinamis dari SharedPreferences kustom lokal kita
        val sharedPref = requireContext().getSharedPreferences("VeloraPref", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("saved_custom_name", "Benediktus")
        val savedAvatar = sharedPref.getString("saved_avatar_uri", null)

        tvName.text = savedName

        // Hitung statistik dari database dummy global kita
        val dummyList = DummyDataRepository.generatePremiumGallery()
        tvTotalAssets.text = dummyList.size.toString()

        val sumLikes = dummyList.sumOf { it.likes }
        tvTotalLikes.text = String.format("%.1fK", sumLikes / 1000.0)

        // Render Avatar
        val avatarTarget: Any = if (savedAvatar != null) Uri.parse(savedAvatar) else "https://images.unsplash.com/photo-1155066931-4365d14bab8c?q=80&w=300"
        Glide.with(this).load(avatarTarget).circleCrop().into(ivAvatar)

        return view
    }
}