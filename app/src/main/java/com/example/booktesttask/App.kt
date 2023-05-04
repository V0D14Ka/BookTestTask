package com.example.booktesttask

import android.content.Context
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.models.book.BookSource

object App {

    private lateinit var appContext: Context

//    private val sourcesProvider: SourcesProvider by lazy {
//        SourceProviderHolder.sourcesProvider
//    }

    // --- sources

//    private val bookSource: BookSource by lazy {
//        sourcesProvider.getBookSource()
//    }

    // --- repositories

    val bookRepository: BookRepository by lazy {
        BookRepository()
    }

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(appContext: Context) {
        App.appContext = appContext
    }
}