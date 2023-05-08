package com.example.booktesttask.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booktesttask.App
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.models.user.UserRepository
import com.example.booktesttask.screens.InfoViewModel
import com.example.booktesttask.screens.SplashViewModel
import com.example.booktesttask.screens.StartViewModel
import com.example.booktesttask.screens.TinderViewModel




class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            TinderViewModel::class.java -> {
                TinderViewModel(BookRepository.get()!!)
            }
            InfoViewModel::class.java -> {
                InfoViewModel()
            }
            StartViewModel::class.java -> {
                StartViewModel()
            }
            SplashViewModel::class.java -> {
                SplashViewModel(UserRepository.get()!!)
            }

            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory()