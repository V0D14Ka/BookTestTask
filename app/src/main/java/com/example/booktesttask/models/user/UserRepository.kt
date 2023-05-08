package com.example.booktesttask.models.user

import android.content.Context
import com.example.booktesttask.models.Repository
import com.example.booktesttask.models.book.BookRepository

class UserRepository private constructor(context: Context): Repository {

    fun isSignedIn(): Boolean {
        return true
    }

    companion object {
        private var INSTANCE:UserRepository? = null

        fun init(context: Context) {
            INSTANCE = UserRepository(context)
        }

        fun get(): UserRepository? {
            return INSTANCE
        }
    }
}