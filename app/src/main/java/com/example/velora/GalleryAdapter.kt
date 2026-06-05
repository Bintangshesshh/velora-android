package com.example.velora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GalleryAdapter(private val listItems: List<GalleryItem>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivGalleryImage)
        val tvTitle: TextView = view.findViewById(R.id.tvGalleryTitle)
        val tvViews: TextView = view.findViewById(R.id.tvGalleryViews)
        val tvLikes: TextView = view.findViewById(R.id.tvLikeCount)
        val tvComments: TextView = view.findViewById(R.id.tvCommentCount)
        val btnLike: LinearLayout = view.findViewById(R.id.btnLikeClick)
        val btnComment: LinearLayout = view.findViewById(R.id.btnCommentClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = listItems[position]

        holder.tvTitle.text = item.title
        holder.tvViews.text = "${item.views} views"
        holder.tvComments.text = "💬 ${item.comments}"

        // Memuat gambar online Unsplash menggunakan Glide secara responsif
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .centerCrop()
            .into(holder.ivImage)

        // Atur status ikon tombol like
        holder.tvLikes.text = if (item.isLiked) "❤️ ${item.likes}" else "🤍 ${item.likes}"

        // Interaksi klik Like / Dislike
        holder.btnLike.setOnClickListener {
            if (item.isLiked) {
                item.likes--
                item.isLiked = false
            } else {
                item.likes++
                item.isLiked = true
                Toast.makeText(holder.itemView.context, "Liked by ${item.creator}", Toast.LENGTH_SHORT).show()
            }
            notifyItemChanged(position)
        }

        // Interaksi klik Komentar
        holder.btnComment.setOnClickListener {
            item.comments++
            holder.tvComments.text = "💬 ${item.comments}"
            Toast.makeText(holder.itemView.context, "Opening comment node for node #${item.id}...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listItems.size
}