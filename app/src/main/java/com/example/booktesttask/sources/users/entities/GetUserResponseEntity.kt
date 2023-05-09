package com.example.booktesttask.sources.users.entities

import com.example.booktesttask.models.user.User

data class GetUserResponseEntity(
    val Id: Long,
    val username: String,
    val favorite_books: Array<String>?,
    val already_read_books: Array<String>?,
    val top: Array<String>?
) {
    fun toUser(): User = User(
        favorite_books = favorite_books,
        dislike_books = null,
        already_read_books = already_read_books,
        username = "username",
        id = Id,
        top = top
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetUserResponseEntity

        if (Id != other.Id) return false
        if (username != other.username) return false
        if (favorite_books != null) {
            if (other.favorite_books == null) return false
            if (!favorite_books.contentEquals(other.favorite_books)) return false
        } else if (other.favorite_books != null) return false
        if (already_read_books != null) {
            if (other.already_read_books == null) return false
            if (!already_read_books.contentEquals(other.already_read_books)) return false
        } else if (other.already_read_books != null) return false
        if (top != null) {
            if (other.top == null) return false
            if (!top.contentEquals(other.top)) return false
        } else if (other.top != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + (favorite_books?.contentHashCode() ?: 0)
        result = 31 * result + (already_read_books?.contentHashCode() ?: 0)
        result = 31 * result + (top?.contentHashCode() ?: 0)
        return result
    }
}