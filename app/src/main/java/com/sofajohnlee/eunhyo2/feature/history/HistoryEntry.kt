package com.sofajohnlee.eunhyo2.feature.history

data class HistoryEntry(
    val title: String,
    val period: String = "",
    val societyCulture: String = "",
    val economy: String = "",
    val politics: String = "",
)

enum class HistoryEra(val label: String) {
    PALEOLITHIC("구석기"),
    NEOLITHIC("신석기"),
    BRONZE("청동기"),
    GOJOSEON("고조선"),
    ANCIENT_STATES("고대 국가"),
    THREE_KINGDOMS("삼국"),
    UNIFIED_SILLA("통일신라"),
    LATER_THREE_KINGDOMS("후삼국"),
    GORYEO("고려"),
    JOSEON("조선"),
    COLONIAL_PERIOD("일제강점기"),
    MODERN("현대"),
}
