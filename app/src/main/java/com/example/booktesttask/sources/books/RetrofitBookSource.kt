package com.example.booktesttask.sources.books

import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookSource
import com.example.booktesttask.sources.base.BaseRetrofitSource
import com.example.booktesttask.sources.base.RetrofitConfig

class RetrofitBookSource (
    config: RetrofitConfig
): BaseRetrofitSource(config), BookSource {

    private val booksApi =
        retrofit.create(BookApi::class.java)

    override suspend fun getRecommendations(): List<Book> = wrapRetrofitExceptions {
        booksApi.getRecommendations()
    }

    override suspend fun genreUpdated(genres: List<String>) {
        booksApi.updatedGenres(genres)
    }

    override suspend fun getBook(id: Long) : Book = wrapRetrofitExceptions {
        booksApi.getBook(id)
    }

    override suspend fun like(id: Long): Unit = wrapRetrofitExceptions{
        booksApi.like(id)
    }

    override suspend fun dislike(id: Long): Unit = wrapRetrofitExceptions{
        booksApi.dislike(id)
    }

    override suspend fun alreadyRead(id: Long): Unit = wrapRetrofitExceptions{
        booksApi.alreadyRead(id)
    }

    override suspend fun skip(id: Long): Unit = wrapRetrofitExceptions{
        booksApi.skip(id)
    }

}