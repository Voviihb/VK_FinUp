package com.vk_edu.finup.data

data class AccountError(
    val accountName: String = "Error",
    val accountLogo: Int = LogoStorage.getImage(ItemLogo.NO_LOGO),
    val totalMoney: Double = 0.0
) : IAccount