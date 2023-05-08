package com.example.booktesttask.sources.users

import com.example.booktesttask.sources.users.entities.GetUserResponseEntity
import com.example.booktesttask.sources.users.entities.SignInRequestEntity
import com.example.booktesttask.sources.users.entities.SignInResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsersApi {

    @POST("auth/token/login")
    suspend fun signIn(
        @Body signInRequestEntity: SignInRequestEntity
    ): SignInResponseEntity

    @GET("login")
    suspend fun getUser(): GetUserResponseEntity

}