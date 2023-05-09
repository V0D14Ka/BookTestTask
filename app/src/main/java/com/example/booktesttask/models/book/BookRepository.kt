package com.example.booktesttask.models.book;

import android.content.Context
import android.util.Log
import com.example.booktesttask.models.Repository
import com.example.booktesttask.utils.BackendException
import com.example.booktesttask.utils.InvalidCredentialsException
import com.example.booktesttask.utils.InvalidInputException
import com.github.javafaker.App
import com.github.javafaker.Faker


typealias BookListener = (books: List<Book>) -> Unit

class BookRepository private constructor(context: Context, private val bookSource: BookSource): Repository {
    private val appContext: Context = context
    private var books = mutableListOf<Book>()
    private val listeners = mutableListOf<BookListener>()
    private val genres = mutableListOf<String>()

    fun addListener(listener: BookListener){
        listeners.add(listener)
        listener.invoke(books)
    }

    fun removeListener(listener: BookListener){
        listeners.remove(listener)
    }

    suspend fun like(item: Book) {
        deleteBook(item)
        notifyChanges()
        try {
            bookSource.like(item.id)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }

    }

    suspend fun dislike(item: Book) {
        deleteBook(item)
        notifyChanges()
        try {
            bookSource.dislike(item.id)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }
    }

    suspend fun alreadyRead(item: Book) {
        deleteBook(item)
        notifyChanges()
        try {
            bookSource.alreadyRead(item.id)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }
    }

    suspend fun skip(item: Book) {
        deleteBook(item)
        notifyChanges()
        try {
            bookSource.skip(item.id)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }
    }

    suspend fun getRecommendations() {
        try {
            books = bookSource.getRecommendations().toMutableList()
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }
    }

    fun addBook(item: Book) {
        books.add(item)
        notifyChanges()
    }
    fun addGenre(g: String){
        genres.add(genres.size,g)
    }

    fun removeGenre(g: String){
        val index = genres.indexOfFirst { it == g }
        genres.removeAt(index)
    }

    suspend fun confirmGenres() {
        try {
            bookSource.genreUpdated(genres)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }
    }

    fun deleteBook(genre: String){
        val index = books.indexOfFirst { it.genre == genre }
        if(index != -1){
            books.removeAt(index)
        }
    }

    fun deleteBook(id: Long){
        val index = books.indexOfFirst { it.id == id }
        if(index != -1){
            books.removeAt(index)
        }
    }

    fun deleteBook(item: Book){
        val index = books.indexOfFirst { it.id == item.id }
        if(index != -1){
            books.removeAt(index)
        }
    }

    private fun notifyChanges(){
        listeners.forEach { it.invoke(books) }
    }

    companion object {
        private var INSTANCE: BookRepository? = null

        fun init(context: Context, source: BookSource) {
            INSTANCE = BookRepository(context, source)
        }

        fun get(): BookRepository {
            return INSTANCE?: throw Exception("Repo must be initialized!")
        }
    }
}
