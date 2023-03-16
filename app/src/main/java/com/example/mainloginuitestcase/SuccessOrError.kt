package com.example.mainloginuitestcase

sealed class SuccessOrError {
    object Succes: SuccessOrError()
    object Error: SuccessOrError()
}