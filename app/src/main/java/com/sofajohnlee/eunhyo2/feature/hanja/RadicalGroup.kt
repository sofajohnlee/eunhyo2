package com.sofajohnlee.eunhyo2.feature.hanja

data class RadicalGroup(
    val id: Int,
    val title: String,
    val entries: List<HanjaEntry>,
)
