package com.example.booktesttask.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookListener
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.utils.share

class TinderViewModel (
    private val bookRepository: BookRepository
        ): ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val posts = _books.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val listener: BookListener = {
        _books.value = it
    }

    init {
        //todo
    }

    data class State(
        val getPostInProgress: Boolean = false,
        val emptyList: Boolean = false,
        val apiFail: Boolean = false

    ) {
        val showProgress: Boolean get() = getPostInProgress
        val enableViews: Boolean get() = !getPostInProgress
        val emptyListInfo: Boolean get() = emptyList
        val apiFailInfo: Boolean get() = apiFail
    }
}