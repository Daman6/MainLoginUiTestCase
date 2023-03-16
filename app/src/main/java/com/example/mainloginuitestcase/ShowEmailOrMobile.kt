package com.example.mainloginuitestcase

sealed class ShowEmailOrMobile {
    object NoOptions: ShowEmailOrMobile()
    object DefaultOptions: ShowEmailOrMobile()
    object EmailOptions: ShowEmailOrMobile()
    object MobileOptions: ShowEmailOrMobile()
}