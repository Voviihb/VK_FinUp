package com.vk_edu.finup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk_edu.finup.data.PreviewOption
import com.vk_edu.finup.data.ResponseStatus
import kotlinx.coroutines.launch
import kotlin.random.Random

class NewOutcomeViewModel : ViewModel() {
    private val EXCEPTION_OCCURRED = "Exception occurred!"

    private val mainRepo: MainRepo = MainRepo()

    private val _options1List = mutableStateListOf<PreviewOption>()
    val options1List: List<PreviewOption> = _options1List

    private val _options2List = mutableStateListOf<PreviewOption>()
    val options2List: List<PreviewOption> = _options2List

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        getData(getCurrentUserId())
    }

    private fun getCurrentUserId(): Int {
        return Random.nextInt(0, 1000)
    }

    private fun getOptions1(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getOptions1(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    if (_options1List.isNotEmpty()) {
                        _options1List.clear()
                    }
                    for (elem in response.options) {
                        _options1List.add(elem)
                    }
                    _loading.value = false
                } else {
                    _options1List += PreviewOption("Error", 1)
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    private fun getOptions2(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getOptions2(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    if (_options2List.isNotEmpty()) {
                        _options2List.clear()
                    }
                    for (elem in response.options) {
                        _options2List.add(elem)
                    }
                    _loading.value = false
                } else {
                    _options2List += PreviewOption("Error", 1)
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    private fun getData(userId: Int) {
        getOptions1(userId)
        getOptions2(userId)
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }
}
