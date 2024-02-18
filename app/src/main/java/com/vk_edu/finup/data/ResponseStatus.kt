package com.vk_edu.finup.data

sealed interface ResponseStatus {
    data object Success : ResponseStatus
    data class Error(val message: String) : ResponseStatus
}
