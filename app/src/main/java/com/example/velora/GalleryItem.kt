package com.example.velora

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryItem(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    var likes: Int,
    val creator: String,
    val uploadDate: String,
    val imageUrl: String,
    var isLiked: Boolean = false
) : Parcelable