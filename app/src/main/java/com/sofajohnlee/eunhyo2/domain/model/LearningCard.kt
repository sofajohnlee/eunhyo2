package com.sofajohnlee.eunhyo2.domain.model

data class LearningCard(
    val primaryText: String,
    val secondaryText: String = "",
    val imageResourceName: String? = null,
    val speechText: String = primaryText,
)
