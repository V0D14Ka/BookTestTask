package com.example.booktesttask.sources

import com.example.booktesttask.models.book.BookSource
import com.example.booktesttask.models.user.UserSource

interface SourcesProvider {
//    fun getUsersSource(): UserSource
    fun getBookSource(): BookSource
}