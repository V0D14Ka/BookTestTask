package com.example.booktesttask.models.user

data class User(
    val id: Long,
    val username: String,
    val email: String?,
    val phone: String,
    val avatar: String,
    val favorite_books: Array<String>?,
    val dislike_books: Array<String>?,
    val already_read_books: Array<String>?,
    val createdAt: Long = UNKNOWN_CREATED_AT
) {
    companion object {
        const val UNKNOWN_CREATED_AT = 0L
    }
}