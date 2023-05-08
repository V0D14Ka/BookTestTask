package com.example.booktesttask.screens

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookListener
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.utils.share
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.coroutines.launch

class TinderViewModel (
    private val bookRepository: BookRepository
        ): ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books = _books.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val listener: BookListener = {
        _books.value = it
    }

    init {
        bookRepository.addListener(listener)
    }

    fun directionToast(context: Context ,direction: Direction) {
        Toast.makeText(context, "Direction $direction", Toast.LENGTH_SHORT).show()
    }
    fun getSize(): Int {
        return _books.value?.size ?: 0
    }

    fun event(direction: Direction, item: Book) = viewModelScope.launch{
        if (direction == Direction.Right) {
            bookRepository.like(item)
        }

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