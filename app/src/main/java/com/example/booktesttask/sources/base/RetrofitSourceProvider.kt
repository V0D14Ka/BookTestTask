package com.example.booktesttask.sources.base

import com.example.booktesttask.models.book.BookSource
import com.example.booktesttask.models.user.UserSource
import com.example.booktesttask.sources.SourcesProvider
import com.example.booktesttask.sources.books.RetrofitBookSource

class RetrofitSourceProvider(
    private val config: RetrofitConfig
) : SourcesProvider {
//    override fun getUsersSource(): UserSource {
//        return RetrofitUsersSource(config)
//    }

    override fun getBookSource(): BookSource {
        return RetrofitBookSource(config)
    }
}