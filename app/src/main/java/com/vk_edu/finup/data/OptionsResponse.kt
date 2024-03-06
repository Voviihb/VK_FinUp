package com.vk_edu.finup.data

data class PreviewOption(val text: String, val id: Int)
data class OptionsResponse(val status: ResponseStatus, val options: List<PreviewOption>)
