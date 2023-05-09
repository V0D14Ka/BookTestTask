package com.example.booktesttask.models.book

import com.example.booktesttask.models.Source

interface BookSource: Source {
    suspend fun getRecommendations() : List<Book>
    suspend fun genreUpdated(genres: List<String>)
    suspend fun getBook(id: Long) : Book
    suspend fun like(id: Long)
    suspend fun dislike(id: Long)
    suspend fun alreadyRead(id: Long)
    suspend fun skip(id: Long)
}