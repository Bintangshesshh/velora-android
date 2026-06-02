package com.example.velora

data class GalleryItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String, // Untuk Filter Kategori
    val imageUrl: String,
    val isLiked: Boolean = false,
    val views: Int,
    var isFavorite: Boolean, // Harus 'var' agar status like bisa diganti-ganti
    val creatorName: String,
    val creatorUsername: String,
    val avatarUrl: String,
    val likesCount: String,
    val spanSize: Int
)