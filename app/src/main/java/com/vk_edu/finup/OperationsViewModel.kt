package com.vk_edu.finup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk_edu.finup.data.ResponseStatus
import kotlinx.coroutines.launch
import kotlin.random.Random

class OperationsViewModel : ViewModel() {
    private val EXCEPTION_OCCURRED = "Exception occurred!"

    private val mainRepo: MainRepo = MainRepo()

    private val _historyList = mutableStateListOf<Array<String>>()
    val historyList: List<Array<String>> = _historyList

    private val _totalMoney = mutableStateOf(0.0)
    val totalMoney: State<Double> = _totalMoney

    private val _spendMoney = mutableStateOf(0.0)
    val spendMoney: State<Double> = _spendMoney

    private val _receiveMoney = mutableStateOf(0.0)
    val receiveMoney: State<Double> = _receiveMoney

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

    private fun getTotalMoney(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getAccounts(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    var total = 0.0
                    for (account in response.accounts)
                        total += account.totalMoney
                    _totalMoney.value = total
                    _loading.value = false
                } else {
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    private fun getSums(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getSums(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    _spendMoney.value = response.spendMoney
                    _receiveMoney.value = response.receiveMoney
                    _loading.value = false
                } else {
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    private fun getHistory(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getHistory(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    if (_historyList.isNotEmpty()) {
                        _historyList.clear()
                    }
                    for (arr in response.history) {
                        _historyList.add(arr)
                    }
                    _loading.value = false
                } else {
                    _historyList += arrayOf(
                        "Error",
                        "Error",
                        "Error",
                        "Error"
                    )
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    private fun getData(userId: Int) {
        getTotalMoney(userId)
        getSums(userId)
        getHistory(userId)
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }
}
