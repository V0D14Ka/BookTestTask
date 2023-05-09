package com.example.booktesttask.models.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.booktesttask.models.Repository
import com.example.booktesttask.models.book.Book
import com.example.booktesttask.models.book.BookRepository
import com.example.booktesttask.models.settings.AppSettings
import com.example.booktesttask.models.settings.SharedPreferencesAppSettings
import com.example.booktesttask.utils.AuthException
import com.example.booktesttask.utils.BackendException
import com.example.booktesttask.utils.EmptyFieldException
import com.example.booktesttask.utils.InvalidCredentialsException
import com.example.booktesttask.utils.InvalidInputException
import com.example.booktesttask.utils.wrapBackendExceptions

typealias InfoListener = (user: User) -> Unit
class UserRepository private constructor(context: Context,private val userSource: UserSource, private val appSettings: AppSettings): Repository {

    val listeners = mutableListOf<InfoListener>()
    private var _currentUser: User? = null

    fun addListener(listener: InfoListener){
        listeners.add(listener)
        listener.invoke(_currentUser!!)
    }

    fun removeListener(listener: InfoListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach { it.invoke(_currentUser!!) }
    }
    fun isSignedIn(): Boolean {
        // user is signed-in if auth token exists
        return appSettings.getCurrentToken() != null
    }

    suspend fun feedback(email: String, msg: String) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (msg.isBlank()) throw EmptyFieldException(Field.Message)

        try {
            userSource.feedback(email, msg)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun signIn(username: String, password: String) {
        if (username.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        val token = try {
            userSource.signIn(username, password)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }
        val tok = "Token $token"
        // success! got auth token -> save it
        appSettings.setCurrentToken(tok)
    }

    fun logout() {
        appSettings.setCurrentToken(null)
    }

    suspend fun getInfo() {
        _currentUser = getAccount()
        notifyChanges()
    }
    suspend fun getAccount(): User = wrapBackendExceptions {
        try {
            userSource.getUser()
        } catch (e: BackendException) {
            // account has been deleted = session expired = AuthException
            if (e.code == 404) throw AuthException(e)
            else throw e
        }
    }

    companion object {
        private var INSTANCE:UserRepository? = null

        fun init(context: Context, userSource: UserSource) {
            INSTANCE = UserRepository(context, userSource, SharedPreferencesAppSettings.get())
        }

        fun get(): UserRepository {
            return INSTANCE?: throw Exception("Repo must be initialized!")
        }
    }
}