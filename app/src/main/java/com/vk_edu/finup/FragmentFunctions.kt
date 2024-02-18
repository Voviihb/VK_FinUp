package com.vk_edu.finup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun launchNextScreen(parentFragmentManager: FragmentManager, func: () -> Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(
            R.id.fragment_container,
            func()
        )
        .commit()
}

fun addFragmentBackStack(parentFragmentManager: FragmentManager, func: () -> Fragment) {
    parentFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, func())
        .addToBackStack(null)
        .commit()
}