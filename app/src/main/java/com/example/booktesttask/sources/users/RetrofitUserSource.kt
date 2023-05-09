package com.example.booktesttask.sources.users

import com.example.booktesttask.models.user.User
import com.example.booktesttask.models.user.UserSource
import com.example.booktesttask.sources.base.BaseRetrofitSource
import com.example.booktesttask.sources.base.RetrofitConfig
import com.example.booktesttask.sources.users.entities.FeedbackRequestEntity
import com.example.booktesttask.sources.users.entities.SignInRequestEntity

class RetrofitUsersSource (
    config: RetrofitConfig
): BaseRetrofitSource(config), UserSource {

    private val usersApi =
        retrofit.create(UsersApi::class.java)

    override suspend fun signIn(username: String, password: String
    ): String = wrapRetrofitExceptions{
        val signInRequestEntity = SignInRequestEntity(
            username = username,
            password = password
        )
        usersApi.signIn(signInRequestEntity).auth_token
    }

    override suspend fun getUser(): User = wrapRetrofitExceptions {
        usersApi.getUser().toUser()
    }

    override suspend fun feedback(email: String, msg: String) = wrapRetrofitExceptions{
        val feedbackRequestEntity = FeedbackRequestEntity(
            email = email,
            message = msg
        )
        usersApi.feedback(feedbackRequestEntity)
    }

}