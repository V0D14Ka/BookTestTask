package com.example.booktesttask.sources.users

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.booktesttask.sources.users.entities.FeedbackRequestEntity
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

    @GET("getinfo")
    suspend fun getUser(): GetUserResponseEntity

    @POST("feedback")
    suspend fun feedback(@Body feedbackRequestEntity: FeedbackRequestEntity): Unit

}