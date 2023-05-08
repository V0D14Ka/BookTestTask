package com.example.booktesttask.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.models.user.UserRepository
import com.example.booktesttask.screens.info.InfoViewModel
import com.example.booktesttask.screens.main.SplashViewModel
import com.example.booktesttask.screens.start.StartViewModel
import com.example.booktesttask.screens.tinder.TinderViewModel




class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            TinderViewModel::class.java -> {
                TinderViewModel(BookRepository.get(), UserRepository.get())
            }
            InfoViewModel::class.java -> {
                InfoViewModel(UserRepository.get())
            }
            StartViewModel::class.java -> {
                StartViewModel()
            }
            SplashViewModel::class.java -> {
                SplashViewModel(UserRepository.get())
            }

            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun <T> LiveData<T>.requireValue(): T {
    return this.value ?: throw IllegalStateException("Value is empty")
}

fun Fragment.factory() = ViewModelFactory()