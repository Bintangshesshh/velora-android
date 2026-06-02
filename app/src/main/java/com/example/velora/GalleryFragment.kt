package com.example.velora

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class GalleryFragment : Fragment() {

    // Database dummy menggunakan struktur data class asli kamu yang val semua
    private val bentoData = mutableListOf(
        GalleryItem("1", "Neon Cyber Core", "Server processing node.", "cyberpunk", "https://images.unsplash.com/photo-1508739773434-c26b3d09e071?q=80&w=600", false, 1420, false, "Benediktus", "ben_dev", "", "5.4k", 2),
        GalleryItem("2", "Biophilic Monolith", "Futuristic structures.", "architecture", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=600", false, 890, false, "Alex", "alex_arch", "", "3.1k", 1),
        GalleryItem("3", "Neural Studio", "Holographic custom monitors.", "workspace", "https://images.unsplash.com/photo-1547082299-de196ea013d6?q=80&w=600", false, 2311, false, "Benediktus", "ben_dev", "", "8.9k", 1),
        GalleryItem("4", "Neo Tokyo Grid", "Shinjuku filtering tech system.", "futuristic", "https://images.unsplash.com/photo-1540959733332-eab4deceeaf7?q=80&w=600", false, 1985, false, "Chen", "schen", "", "6.1k", 2),
        GalleryItem("5", "Android Dreaming", "Synthetic consciousness frame.", "AI", "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?q=80&w=600", false, 3422, false, "Kaelen", "kaelen_ai", "", "12k", 1)
    )

    private val filteredData = mutableListOf<GalleryItem>()
    private lateinit var adapter: BentoGalleryAdapter
    private var currentCategory = "All"

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            prosesUploadFotoKomplit(uri)
        } else {
            Toast.makeText(requireContext(), "Batal memilih gambar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        filteredData.clear()
        filteredData.addAll(bentoData)

        val rvBentoGallery = view.findViewById<RecyclerView>(R.id.rvBentoGallery)
        rvBentoGallery.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Mengatur adapter dengan taktik duplikasi objek (.copy) agar kebal dari error val reassign
        adapter = BentoGalleryAdapter(
            filteredData,
            onLikeClickListener = { item, position ->
                // Siasati mengubah properti val / var dengan membuat kloningan baru
                val statusTerbaru = !item.isFavorite
                val itemTerupdate = item.copy(isFavorite = statusTerbaru)

                // Ganti data lama di list dengan data kloningan baru kita
                val indexDiUtama = bentoData.indexOf(item)
                if (indexDiUtama != -1) bentoData[indexDiUtama] = itemTerupdate
                filteredData[position] = itemTerupdate

                adapter.notifyItemChanged(position)
                val statusText = if (statusTerbaru) "Disukai" else "Batal Menyukai"
                Toast.makeText(requireContext(), "$statusText: ${itemTerupdate.title}", Toast.LENGTH_SHORT).show()
            },
            onEditClickListener = { item, position ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Modify Asset Parameter")

                val layoutDialog = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(50, 30, 50, 30)
                }

                val etTitleInput = EditText(requireContext()).apply {
                    setText(item.title)
                    hint = "Asset Title"
                }

                val etDescInput = EditText(requireContext()).apply {
                    setText(item.description)
                    hint = "Comment / Description"
                }

                layoutDialog.addView(etTitleInput)
                layoutDialog.addView(etDescInput)
                builder.setView(layoutDialog)

                builder.setPositiveButton("Save Node") { dialog, _ ->
                    val judulBaru = etTitleInput.text.toString().trim()
                    val descBaru = etDescInput.text.toString().trim()

                    if (judulBaru.isNotEmpty()) {
                        // Taktik .copy() mengatasi masalah error 'val' cannot be reassigned secara tuntas!
                        val itemTeredit = item.copy(title = judulBaru, description = descBaru)

                        val indexDiUtama = bentoData.indexOf(item)
                        if (indexDiUtama != -1) bentoData[indexDiUtama] = itemTeredit
                        filteredData[position] = itemTeredit

                        adapter.notifyItemChanged(position)
                        Toast.makeText(requireContext(), "Asset updated successfully!", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }

                builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                builder.show()
            },
            onDeleteClickListener = { item, position ->
                bentoData.remove(item)
                filteredData.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, filteredData.size)
                Toast.makeText(requireContext(), "${item.title} deleted.", Toast.LENGTH_SHORT).show()
            }
        )
        rvBentoGallery.adapter = adapter

        // Widget Kontrol Filter UI Kategori
        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val btnFilterAll = view.findViewById<Button>(R.id.btnFilterAll)
        val btnFilterCyber = view.findViewById<Button>(R.id.btnFilterCyber)
        val btnFilterArch = view.findViewById<Button>(R.id.btnFilterArch)
        val btnFilterAI = view.findViewById<Button>(R.id.btnFilterAI)
        val btnUploadSimulasi = view.findViewById<Button>(R.id.btnUploadSimulasi)

        btnUploadSimulasi.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        fun applyFilterAndSearch() {
            val query = etSearch.text.toString().trim()
            filteredData.clear()

            val matchesList = bentoData.filter { item ->
                val matchesSearch = item.title.contains(query, ignoreCase = true) ||
                        item.description.contains(query, ignoreCase = true)
                val matchesCategory = currentCategory == "All" ||
                        item.category.equals(currentCategory, ignoreCase = true)
                matchesSearch && matchesCategory
            }

            filteredData.addAll(matchesList)
            adapter.notifyDataSetChanged()
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { applyFilterAndSearch() }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnFilterAll.setOnClickListener { currentCategory = "All"; applyFilterAndSearch() }
        btnFilterCyber.setOnClickListener { currentCategory = "cyberpunk"; applyFilterAndSearch() }
        btnFilterArch.setOnClickListener { currentCategory = "architecture"; applyFilterAndSearch() }
        btnFilterAI.setOnClickListener { currentCategory = "AI"; applyFilterAndSearch() }

        return view
    }

    private fun prosesUploadFotoKomplit(uri: Uri) {
        val idBaru = System.currentTimeMillis().toString()
        val authManager = AuthManager(requireContext())
        val namaUserAktif = authManager.getUsername() ?: "Benediktus"

        val assetBaru = GalleryItem(
            id = idBaru,
            title = "Asset Node #$idBaru",
            description = "Custom storage local upload framework.",
            category = "cyberpunk",
            imageUrl = uri.toString(),
            isLiked = true,
            views = 1,
            isFavorite = false,
            creatorName = namaUserAktif,
            creatorUsername = "ben_dev",
            avatarUrl = "",
            likesCount = "1",
            spanSize = 1
        )

        bentoData.add(0, assetBaru)
        filteredData.add(0, assetBaru)
        adapter.notifyItemInserted(0)

        view?.findViewById<RecyclerView>(R.id.rvBentoGallery)?.scrollToPosition(0)
        Toast.makeText(requireContext(), "Asset Komplit Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show()
    }
}