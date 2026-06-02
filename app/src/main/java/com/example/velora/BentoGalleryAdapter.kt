package com.example.velora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BentoGalleryAdapter(
    private val items: List<GalleryItem>,
    private val onLikeClickListener: (GalleryItem, Int) -> Unit, // Tambah parameter posisi
    private val onEditClickListener: (GalleryItem, Int) -> Unit,
    private val onDeleteClickListener: (GalleryItem, Int) -> Unit
) : RecyclerView.Adapter<BentoGalleryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivGalleryImage)
        val tvTitle: TextView = view.findViewById(R.id.tvGalleryTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvGalleryDescription)
        val tvCreator: TextView = view.findViewById(R.id.tvGalleryCreator)
        val tvLikes: TextView = view.findViewById(R.id.tvGalleryLikes)
        val tvViews: TextView = view.findViewById(R.id.tvGalleryViews)
        val btnEdit: Button = view.findViewById(R.id.btnEditAsset)
        val btnDelete: Button = view.findViewById(R.id.btnDeleteAsset)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bento_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.tvTitle.text = item.title
        holder.tvDescription.text = item.description
        holder.tvCreator.text = "👤 By: ${item.creatorName}"
        holder.tvViews.text = "👁️ ${item.views} Views"
        holder.tvLikes.text = "❤️ ${item.likesCount} Likes"

        // Menggunakan Glide untuk merender gambar asli dari internal/internet
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.ivImage)

        // Indikasi status jika tombol like/favorite aktif
        if (item.isFavorite) {
            holder.tvLikes.text = "❤️ ${item.likesCount} (Liked)"
        }

        // Klik pada kartu / gambar untuk Like
        holder.itemView.setOnClickListener {
            onLikeClickListener(item, position)
        }

        // Klik Tombol Edit
        holder.btnEdit.setOnClickListener {
            onEditClickListener(item, position)
        }

        // Klik Tombol Hapus
        holder.btnDelete.setOnClickListener {
            onDeleteClickListener(item, position)
        }
    }

    override fun getItemCount(): Int = items.size
}