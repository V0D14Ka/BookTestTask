package com.example.booktesttask.models.book

data class Book (
    val id: Long,
    val author: String,
    val name: String,
    val genre: String,
    val description: String,
    val price: Long,
    val preview: String,
    var isLiked: Boolean,
    val isRead: Boolean,
        )