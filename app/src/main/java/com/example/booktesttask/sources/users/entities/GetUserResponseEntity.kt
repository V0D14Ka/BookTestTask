package com.example.booktesttask.sources.users.entities

import com.example.booktesttask.models.user.User

data class GetUserResponseEntity(
    val Id: Long,
    val username: String,
    val favorite_books: Array<String>?,
    val already_read_books: Array<String>?,
) {
    fun toUser(): User = User(
        favorite_books = favorite_books,
        dislike_books = null,
        already_read_books = already_read_books,
        username = "username",
        id = Id
    )
}