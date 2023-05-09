package com.example.booktesttask.screens.info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktesttask.models.user.Field
import com.example.booktesttask.models.user.InfoListener
import com.example.booktesttask.models.user.User
import com.example.booktesttask.models.user.UserRepository
import com.example.booktesttask.screens.tinder.TinderViewModel
import com.example.booktesttask.utils.EmptyFieldException
import com.example.booktesttask.utils.InvalidCredentialsException
import com.example.booktesttask.utils.InvalidInputException
import com.example.booktesttask.utils.requireValue
import com.example.booktesttask.utils.share
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user = _user.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _dialogState = MutableLiveData(DialogState())
    val dialogState = _dialogState.share()

    private val listener: InfoListener = {
        _user.value = it
    }

    fun getUser() = viewModelScope.launch {
        showProgress()
        delay(500)
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

    fun getTopGenres(): String {
        var msg = ""
        user.value?.top?.forEach {
            msg += it
            msg += """, """
        }
        msg = msg.removeSuffix(", ")
        if (msg == "0") msg = "Нет информации"
        return msg
    }

    fun getShareInfo(): String {
        return """Моя статистика: ${user.value?.already_read_books?.size.toString()} книг прочитано, 
                |${user.value?.favorite_books?.size.toString()} книг понравилось. Топ 3 жанра: 
                |${getTopGenres()} """.trimMargin()
    }

    fun submitFeedback(email: String, feedback: String) = viewModelScope.launch{
        try {
            userRepository.feedback(email, feedback)
            processOk()
        } catch (e: EmptyFieldException) {
            Log.e("Iwas", "Я был здесь")
            processEmptyFieldException(e)
        } catch (e: Exception) {
            Log.e("FeedbackError", e.message.toString())
        }
    }

    private fun processOk() {
        _dialogState.value = _dialogState.requireValue().copy(
            processOk = true
        )
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _dialogState.value = _dialogState.requireValue().copy(
            emptyEmailError = e.field == Field.Email,
            emptyMessageError = e.field == Field.Message
        )
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

    override fun onCleared() {
        super.onCleared()
        userRepository.removeListener(listener)
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

    data class DialogState(
        val emptyEmailError: Boolean = false,
        val emptyMessageError: Boolean = false,
        val processOk: Boolean = false
    )
}