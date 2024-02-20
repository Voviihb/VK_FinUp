package com.vk_edu.finup

import com.vk_edu.finup.data.AccountDomain
import com.vk_edu.finup.data.AccountsResponse
import com.vk_edu.finup.data.BankDomain
import com.vk_edu.finup.data.BanksResponse
import com.vk_edu.finup.data.HistoryResponse
import com.vk_edu.finup.data.ItemLogo
import com.vk_edu.finup.data.LoginResponse
import com.vk_edu.finup.data.ResponseStatus
import com.vk_edu.finup.data.SumsResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainRepo() {
    private val DEFAULT_ACCOUNT_EMAIL = "mail@mail.ru"
    private val DEFAULT_ACCOUNT_PASSWORD = "Qwerty"
    private val DELAY_TIME = 2000L

    suspend fun authUser(email: String, password: String) = withContext(Dispatchers.IO) {
        val status = ResponseStatus.Success //ResponseStatus.Error("Login error occurred!")
        val result = (email == DEFAULT_ACCOUNT_EMAIL && password == DEFAULT_ACCOUNT_PASSWORD)

        delay(DELAY_TIME)
        return@withContext LoginResponse(status = status, result = result)
    }


    suspend fun getAccounts(userId: Int = 0) = withContext(Dispatchers.IO) {
        val accountsList = mutableListOf<AccountDomain>()
        val status = ResponseStatus.Success //ResponseStatus.Error("Fetching accounts error!")
        for (i in 1..20) {
            accountsList.add(
                AccountDomain(
                    "Account $i",
                    when ((i - 1) % 4) {
                        0 -> ItemLogo.TINKOFF
                        1 -> ItemLogo.SBER
                        2 -> ItemLogo.AlFA
                        else -> ItemLogo.CASH
                    },
                    1_000_000.0 / i
                )
            )
        }

        delay(DELAY_TIME)
        return@withContext AccountsResponse(status, accountsList)
    }

    suspend fun getBanks(userId: Int = 0) = withContext(Dispatchers.IO) {
        val banksList = mutableListOf(
            BankDomain("Tinkoff", ItemLogo.TINKOFF),
            BankDomain("Sber", ItemLogo.SBER),
            BankDomain("AlfaBank", ItemLogo.AlFA)
        )
        val status = ResponseStatus.Success //ResponseStatus.Error("Fetching banks error!")

        delay(DELAY_TIME)
        return@withContext BanksResponse(status, banksList)
    }

    suspend fun getSums(userId: Int = 0) = withContext(Dispatchers.IO) {
        val status = ResponseStatus.Success //ResponseStatus.Error("Fetching sums error!")

        delay(DELAY_TIME)
        return@withContext SumsResponse(status, 57400.0, 66800.0)
    }

    suspend fun getHistory(userId: Int = 0) = withContext(Dispatchers.IO) {
        val HistoryList = mutableListOf(
            arrayOf(
                "Продукты",
                "Карта **** **** **** 8943",
                "5 октября 2023 г.",
                "-1 100 ₽"
            ),
            arrayOf(
                "Зарплата",
                "Карта **** **** **** 6532",
                "2 октября 2023 г.",
                "+10 500 ₽"
            ),
            arrayOf(
                "Продукты",
                "Карта **** **** **** 8943",
                "5 октября 2023 г.",
                "-1 100 ₽"
            ),
            arrayOf(
                "Зарплата",
                "Карта **** **** **** 6532",
                "2 октября 2023 г.",
                "+10 500 ₽"
            ),
            arrayOf(
                "Продукты",
                "Карта **** **** **** 8943",
                "5 октября 2023 г.",
                "-1 100 ₽"
            ),
            arrayOf(
                "Зарплата",
                "Карта **** **** **** 6532",
                "2 октября 2023 г.",
                "+10 500 ₽"
            )
        )
        val status = ResponseStatus.Success //ResponseStatus.Error("Fetching history error!")

        delay(DELAY_TIME)
        return@withContext HistoryResponse(status, HistoryList)
    }
}