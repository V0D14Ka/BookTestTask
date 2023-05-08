package com.example.booktesttask.models.user

data class User(
    val id: Long,
    val username: String,
    val favorite_books: Array<String>?,
    val dislike_books: Array<String>?,
    val already_read_books: Array<String>?,
) {
    companion object {
        const val UNKNOWN_CREATED_AT = 0L
    }
}