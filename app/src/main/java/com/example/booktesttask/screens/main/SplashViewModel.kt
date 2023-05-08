package com.example.booktesttask.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktesttask.models.user.UserRepository
import com.example.booktesttask.utils.MutableLiveEvent
import com.example.booktesttask.utils.publishEvent
import com.example.booktesttask.utils.share
import kotlinx.coroutines.launch

class SplashViewModel(
    private val usersRepository: UserRepository
) : ViewModel() {

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(usersRepository.isSignedIn())
        }
    }
}