package com.example.booktesttask.models.book;

import com.example.booktesttask.models.Repository
import com.github.javafaker.Faker


typealias BookListener = (books: List<Book>) -> Unit
typealias LikedBookListener = (likedbooks: List<Book>) -> Unit

class BookRepository : Repository {
    private var books = mutableListOf<Book>()
    private val listeners = mutableListOf<BookListener>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        books = (1..10).map {
            Book(
                author = faker.book().author(),
                price = 123,
                description = faker.lorem().characters(),
                name = faker.book().genre(),
                preview = IMAGES[it % IMAGES.size]
            )
        }.toMutableList()
    }

    fun addListener(listener: BookListener){
        listeners.add(listener)
        listener.invoke(books)
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80",
            "https://images.unsplash.com/photo-1576872381149-7847515ce5d8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
            "https://images.unsplash.com/photo-1544947950-fa07a98d237f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
            "https://images.unsplash.com/photo-1543497415-75c0a27177c0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=627&q=80"
        )
    }
}
