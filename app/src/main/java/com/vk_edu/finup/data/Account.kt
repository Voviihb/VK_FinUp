package com.vk_edu.finup.data

data class Account(
    val accountName: String,
    val accountLogo: Int,
    val totalMoney: Double
) : IAccount
