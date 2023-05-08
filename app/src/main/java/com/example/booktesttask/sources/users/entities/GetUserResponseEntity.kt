package com.example.booktesttask.sources.users.entities

import com.example.booktesttask.models.user.User

data class GetUserResponseEntity(
    val Id: Long,
    val username: String,
    val phone: String,
    val avatar: String
) {
    fun toUser(): User = User(
        email = null,
        phone = phone,
        favorite_books = null,
        dislike_books = null,
        already_read_books = null,
        username = username,
        id = Id,
        avatar = avatar
    )
}