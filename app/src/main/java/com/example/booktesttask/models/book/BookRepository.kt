package com.example.booktesttask.models.book;

import android.content.Context
import android.util.Log
import com.example.booktesttask.models.Repository
import com.github.javafaker.App
import com.github.javafaker.Faker


typealias BookListener = (books: List<Book>) -> Unit

class BookRepository private constructor(context: Context, private val bookSource: BookSource): Repository {
    private val appContext: Context = context
    private var books = mutableListOf<Book>()
    private val listeners = mutableListOf<BookListener>()
    private val genres = mutableListOf<String>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        books = (1..10).map {
            Book(
                id = it.toLong(),
                author = faker.book().author(),
                price = 123,
                description = faker.lorem().characters(),
                name = faker.book().title(),
                genre = faker.book().genre(),
                preview = IMAGES[it % IMAGES.size],
                isLiked = false,
                isRead = false
            )
        }.toMutableList()
    }

    fun addListener(listener: BookListener){
        listeners.add(listener)
        listener.invoke(books)
    }

    suspend fun like(item: Book) {
        deleteBook(item)
        notifyChanges()
        bookSource.like(item.id)
    }

    suspend fun dislike(item: Book) {
        deleteBook(item)
        notifyChanges()
        bookSource.dislike(item.id)
    }

    suspend fun alreadyRead(item: Book) {
        deleteBook(item)
        notifyChanges()
        bookSource.alreadyRead(item.id)
    }

    suspend fun skip(item: Book) {
        deleteBook(item)
        notifyChanges()
        bookSource.skip(item.id)
    }

    fun addGenre(g: String){
        genres.add(genres.size,g)
    }

    fun removeGenre(g: String){
        val index = genres.indexOfFirst { it == g }
        genres.removeAt(index)
    }

    suspend fun confirmGenres() {
        bookSource.genreUpdated(genres)
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
        private val IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80",
            "https://images.unsplash.com/photo-1576872381149-7847515ce5d8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
            "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
            "https://images.unsplash.com/photo-1543497415-75c0a27177c0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=627&q=80"
        )

        private var INSTANCE: BookRepository? = null

        fun init(context: Context, source: BookSource) {
            INSTANCE = BookRepository(context, source)
        }

        fun get(): BookRepository {
            return INSTANCE?: throw Exception("Repo must be initialized!")
        }
    }
}
