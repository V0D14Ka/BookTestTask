package com.example.booktesttask.screens.tinder

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookListener
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.models.user.UserRepository
import com.example.booktesttask.utils.EmptyFieldException
import com.example.booktesttask.utils.InvalidCredentialsException
import com.example.booktesttask.utils.InvalidInputException
import com.example.booktesttask.utils.requireValue
import com.example.booktesttask.utils.share
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TinderViewModel (
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository
        ): ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books = _books.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val listener: BookListener = {
        _books.value = it
    }

    init {
        getRecommendations()
    }

    fun directionToast(context: Context ,direction: Direction) {
        Toast.makeText(context, "Direction $direction", Toast.LENGTH_SHORT).show()
    }
    fun getSize(): Int {
        return _books.value?.size ?: 0
    }

    fun getRecommendations() = viewModelScope.launch {
        showProgress()
        delay(1000)
        try {
            bookRepository.getRecommendations()
        } catch (e: Exception) {
            apiFail()
        }
        finally {
            bookRepository.addListener(listener)
            hideProgress()
        }
    }
    fun event(direction: Direction, item: Book) = viewModelScope.launch{
        try {
            if (direction == Direction.Right) {
                bookRepository.like(item)
            } else if (direction == Direction.Left) {
                bookRepository.dislike(item)
            } else if (direction == Direction.Top) {
                bookRepository.alreadyRead(item)
            } else {
                bookRepository.skip(item)
            }
        } catch (e: Exception) {
            bookRepository.addBook(item)
        }
    }

    private fun showProgress() {
        _state.value = _state.requireValue().copy(emptyList = false, getBookInProgress = true, apiFail = false)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(getBookInProgress = false)
        if (_books.value.isNullOrEmpty())
            _state.value = _state.requireValue().copy(emptyList = true)
    }

    private fun apiFail() {
        _state.value = _state.requireValue().copy(apiFail = true)
    }

    override fun onCleared() {
        super.onCleared()
        bookRepository.removeListener(listener)
    }

    data class State(
        val getBookInProgress: Boolean = false,
        val emptyList: Boolean = false,
        val apiFail: Boolean = false

    ) {
        val showProgress: Boolean get() = getBookInProgress
        val enableViews: Boolean get() = !getBookInProgress
        val emptyListInfo: Boolean get() = emptyList
        val apiFailInfo: Boolean get() = apiFail
    }
}