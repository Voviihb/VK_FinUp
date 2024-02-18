package com.vk_edu.finup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk_edu.finup.data.Account
import com.vk_edu.finup.data.AccountDomain
import com.vk_edu.finup.data.AccountError
import com.vk_edu.finup.data.Bank
import com.vk_edu.finup.data.IAccount
import com.vk_edu.finup.data.LogoStorage
import com.vk_edu.finup.data.ResponseStatus
import kotlinx.coroutines.launch
import kotlin.random.Random

class AccountsViewModel : ViewModel() {
    private val EXCEPTION_OCCURRED = "Exception occurred!"

    private val mainRepo: MainRepo = MainRepo()

    private val _accountsList = mutableStateListOf<IAccount>()
    val accountsList: List<IAccount> = _accountsList

    private val _banksList = mutableStateListOf<Bank>()
    val banksList: List<Bank> = _banksList

    private val _totalMoney = mutableStateOf(0.0)
    val totalMoney: State<Double> = _totalMoney

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        getAccountsScreenData(getCurrentUserId())
    }

    // заглушка, позже будет реализована
    private fun getCurrentUserId(): Int {
        return Random.nextInt(0, 1000)
    }

    private fun getAccounts(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response =
                    mainRepo.getAccounts(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    if (_accountsList.isNotEmpty()) {
                        _accountsList.clear()
                    }
                    for (account in response.accounts) {
                        _accountsList.add(
                            Account(
                                account.accountName,
                                LogoStorage.getImage(account.accountLogo),
                                account.totalMoney
                            )
                        )
                    }
                    _totalMoney.value = getTotalMoney(response.accounts)
                    _loading.value = false
                } else {
                    _accountsList += AccountError()
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    private fun getBanks(userId: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = mainRepo.getBanks(userId = userId)
                if (response.status == ResponseStatus.Success) {
                    if (_banksList.isNotEmpty()) {
                        _banksList.clear()
                    }
                    for (bank in response.banks) {
                        _banksList.add(
                            Bank(bank.bankName, LogoStorage.getImage(bank.bankLogo))
                        )
                    }
                    _loading.value = false
                } else {
                    _banksList += Bank("Error", R.drawable.error_icon)
                    onError((response.status as ResponseStatus.Error).message)
                }
            } catch (e: Exception) {
                onError(e.message ?: EXCEPTION_OCCURRED)
            }
        }
    }

    private fun getAccountsScreenData(userId: Int) {
        getAccounts(userId)
        getBanks(userId)
    }

    private fun getTotalMoney(accounts: List<AccountDomain>): Double {
        var total = 0.0
        for (account in accounts) {
            total += account.totalMoney
        }
        return total
    }


    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
