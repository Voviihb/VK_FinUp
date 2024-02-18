package com.vk_edu.finup.data

import com.vk_edu.finup.R

class LogoStorage {
    companion object {
        fun getImage(name: ItemLogo) =
            when (name) {
                ItemLogo.TINKOFF -> R.drawable.tinkoff_logo
                ItemLogo.SBER -> R.drawable.sber_logo
                ItemLogo.AlFA -> R.drawable.alfa_logo
                ItemLogo.CASH -> R.drawable.cash_icon
                else -> R.drawable.error_icon
            }
    }
}
