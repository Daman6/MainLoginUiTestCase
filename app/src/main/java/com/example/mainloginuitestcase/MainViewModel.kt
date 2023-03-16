package com.example.mainloginuitestcase

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _hasFocus = MutableStateFlow(false)
    val hasFocus = _hasFocus.asStateFlow()

    fun updateFocus(data: Boolean) {
        _hasFocus.value = data
    }

    private val _loginCheckBox = MutableStateFlow(false)
    val loginCheckBox = _loginCheckBox.asStateFlow()

    fun update_loginCheckBox(data: Boolean) {
        _loginCheckBox.value = data
    }

    private val _textFieldText = MutableStateFlow("")
    val textFieldText = _textFieldText.asStateFlow()

    fun screenChanged(text: String) {
        _textFieldText.value = text
    }

    fun getShowEmailOrMobile(): ShowEmailOrMobile {
        if (hasFocus.value) {
            if (textFieldText.value.isEmpty()) {
                return ShowEmailOrMobile.DefaultOptions
            } else if (textFieldText.value.first().isDigit()) {
                return ShowEmailOrMobile.MobileOptions
            } else if (textFieldText.value.first().isLetter()) {
                return ShowEmailOrMobile.EmailOptions
            }
            return ShowEmailOrMobile.DefaultOptions
        } else {
            return ShowEmailOrMobile.NoOptions
        }
    }


    fun isValidPhoneNumber(input: String): Boolean {
        val regex = Regex("^\\+?[0-9]{10,12}\$")
        if (regex.matches(input) && input.length in 10..10) {
            return true
        } else {
            return false
        }
    }
    fun isValidPhoneNumber2(input: String): Boolean {
        val regex = Regex("^\\+?[0-9]{10,12}\$")
        if (regex.matches(input) && input.length in 10..10) {
            return true
        } else {
            return false
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        if (email.matches(emailRegex.toRegex())) {
            if (loginCheckBox.value) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    fun isEmpty(input: String): SuccessOrError {
        if (input.isEmpty()) {
            return SuccessOrError.Error
        } else {
            return SuccessOrError.Succes
        }
    }

    fun showToast(msg: String, context: Context) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

}