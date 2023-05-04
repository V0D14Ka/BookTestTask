package com.example.booktesttask.models.book

data class Book (
    val author: String,
    val name: String,
    val description: String,
    val price: Long,
    val preview: String
        )