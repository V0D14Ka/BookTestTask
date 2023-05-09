package com.example.booktesttask.models.user

interface UserSource {
    suspend fun signIn(username: String, password: String) : String
    suspend fun getUser(): User
}