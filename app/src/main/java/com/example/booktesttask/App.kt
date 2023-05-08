package com.example.booktesttask

import android.app.Application
import android.content.Context
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.models.book.BookSource
import com.example.booktesttask.models.settings.SharedPreferencesAppSettings
import com.example.booktesttask.models.user.UserRepository
import com.example.booktesttask.sources.SourceProviderHolder
import com.example.booktesttask.sources.SourcesProvider

class App : Application() {
    private lateinit var sourcesProvider: SourcesProvider
    // --- repositories
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesAppSettings.init(this)
        sourcesProvider = SourceProviderHolder.sourcesProvider
        BookRepository.init(this, sourcesProvider.getBookSource())
        UserRepository.init(this, sourcesProvider.getUserSource())
    }
}