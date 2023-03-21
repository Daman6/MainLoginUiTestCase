package com.example.mainloginuitestcase.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel:ViewModel() {

    private val _nameText = MutableStateFlow("")
    val nameText = _nameText.asStateFlow()

    fun updateNameTextValue(text:String){
        _nameText.value = text
    }


    private val _emailText = MutableStateFlow("")
    val emailText = _emailText.asStateFlow()

    fun updateEmailTextValue(text:String){
        _emailText.value = text
    }

    private val _dateText = MutableStateFlow("")
    val dateText = _dateText.asStateFlow()

    fun updateDateTextValue(text:String){
        _dateText.value = text
    }

    private val _monthText = MutableStateFlow("")
    val monthText = _monthText.asStateFlow()

    fun updateMonthTextValue(text:String){
        _monthText.value = text
    }
    private val _yearText = MutableStateFlow("")
    val yearText = _yearText.asStateFlow()

    fun updateYearTextValue(text:String){
        _yearText.value = text
    }


    private val _isChecked = MutableStateFlow(false)
    var isChecked = _isChecked.asStateFlow()

    fun updateCheckBoxValue(text:Boolean){
        _isChecked.value = text
    }

    private val _isSwitchChecked = MutableStateFlow(false)
    var isSwitchChecked = _isSwitchChecked.asStateFlow()

    fun updateSwitchCheckBoxValue(text:Boolean){
        _isSwitchChecked.value = text
    }

    fun checkValidation():Boolean{
        if (nameText.value.isNotEmpty()){
            if (emailText.value.isNotEmpty()){
                if (isChecked.value){
                    if (isSwitchChecked.value){
                        return true
                    }
                }
            }
        }
        return false
    }

}