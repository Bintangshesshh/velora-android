package com.example.velora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = resources.getIdentifier("fragment_login", "layout", requireActivity().packageName)
        if (layoutId == 0) return null

        val view = inflater.inflate(layoutId, container, false)

        // Otomatis mencari kolom teks dan tombol di dalam layout tanpa memakai ID spesifik
        val editTexts = ArrayList<EditText>()
        val buttons = ArrayList<Button>()
        findViewsRecursively(view, editTexts, buttons)

        // Konfigurasi tombol Sign In (Tombol pertama di layout)
        if (buttons.isNotEmpty()) {
            buttons[0].setOnClickListener {
                if (editTexts.size >= 2) {
                    val email = editTexts[0].text.toString().trim()
                    val password = editTexts[1].text.toString().trim()

                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        val mainAct = activity as? MainActivity
                        mainAct?.isLoggedIn = true
                        mainAct?.updateNavbarVisibility()
                        mainAct?.switchFragment(GalleryFragment())
                        Toast.makeText(context, "Authorization Successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "All parameters required!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Konfigurasi tombol Register (Tombol kedua di layout jika ada)
        if (buttons.size >= 2) {
            buttons[1].setOnClickListener {
                val mainAct = activity as? MainActivity
                mainAct?.switchFragment(RegisterFragment())
            }
        }

        return view
    }

    private fun findViewsRecursively(view: View, editTexts: ArrayList<EditText>, buttons: ArrayList<Button>) {
        if (view is EditText) {
            editTexts.add(view)
        } else if (view is Button) {
            buttons.add(view)
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                findViewsRecursively(view.getChildAt(i), editTexts, buttons)
            }
        }
    }
}