package com.example.velora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryFragment : Fragment() {

    private lateinit var adapter: GalleryAdapter

    // Data Dummy Sesuai Mockup Gambar Estetik Kamu!
    private val dummyGallery = listOf(
        GalleryItem(1, "Cyberpunk Station Neon", "3877 views", "Cyberpunk", 1241, "Neon_X", "04/06/2026", "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=400"),
        GalleryItem(2, "Neural Networks Fluid", "1565 views", "AI", 892, "Core_Dev", "04/06/2026", "https://images.unsplash.com/photo-1515621061946-eff1c2a352bd?w=400"),
        GalleryItem(3, "Minimalist Workspace setup", "7423 views", "Workspace", 2311, "Ben", "03/06/2026", "https://images.unsplash.com/photo-1499951360447-b19be8fe80f5?w=400"),
        GalleryItem(4, "Futuristic Concrete Villa", "1390 views", "Architecture", 456, "Studio_V", "02/06/2026", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=400")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Otomatis mencari file layout fragment_gallery kamu
        val layoutId = resources.getIdentifier("fragment_gallery", "layout", requireActivity().packageName)
        if (layoutId == 0) return null

        val view = inflater.inflate(layoutId, container, false)

        val res = resources
        val pkg = requireActivity().packageName

        // TRIK AMAN: Mencari ID secara fleksibel jika ada perbedaan penamaan di XML kamu
        val rvId = res.getIdentifier("rvGalleryGrid", "id", pkg)
            ?: res.getIdentifier("recyclerView", "id", pkg)
            ?: res.getIdentifier("rv_gallery", "id", pkg)

        val btnUploadId = res.getIdentifier("btnUploadNewAsset", "id", pkg)
            ?: res.getIdentifier("uploadButton", "id", pkg)
            ?: res.getIdentifier("btn_upload", "id", pkg)

        val recyclerView = if (rvId != 0) view.findViewById<RecyclerView>(rvId) else null
        val btnUpload = if (btnUploadId != 0) view.findViewById<View>(btnUploadId) else null

        // Pasang Grid 2 Kolom vertikal mirip layout bento punyamu
        recyclerView?.let {
            adapter = GalleryAdapter(dummyGallery)
            it.layoutManager = GridLayoutManager(context, 2)
            it.adapter = adapter
        }

        // Jalankan navigasi jika tombol upload di klik
        btnUpload?.setOnClickListener {
            val mainAct = activity as? MainActivity
            // Pastikan fungsi switchFragment tersedia di MainActivity kamu
            mainAct?.let { act ->
                val uploadFragmentClass = try {
                    Class.forName("com.example.velora.UploadFragment").newInstance() as Fragment
                } catch (e: Exception) {
                    null
                }
                uploadFragmentClass?.let { frag -> act.switchFragment(frag) }
            }
        }

        return view
    }
}