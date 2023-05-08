package com.example.booktesttask.screens.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.user.InfoListener
import com.example.booktesttask.models.user.User
import com.example.booktesttask.models.user.UserRepository
import com.example.booktesttask.screens.tinder.TinderViewModel
import com.example.booktesttask.utils.requireValue
import com.example.booktesttask.utils.share
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user = _user.share()

    private val _state = MutableLiveData(TinderViewModel.State())
    val state = _state.share()

    private val listener: InfoListener = {
        _user.value = it
    }

    init {
        getUser()
    }

    fun getUser() = viewModelScope.launch {
        showProgress()
        delay(5000)
        try {
            userRepository.getInfo()
            userRepository.addListener(listener)
        }catch (e: Exception) {
            apiFail()
        }
        finally {
            hideProgress()
        }
    }

    private fun showProgress() {
        _state.value = _state.requireValue().copy(emptyList = false, getBookInProgress = true, apiFail = false)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(getBookInProgress = false)
        if (_user.value != null)
            _state.value = _state.requireValue().copy(emptyList = true)
    }

    private fun apiFail() {
        _state.value = _state.requireValue().copy(apiFail = true)
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