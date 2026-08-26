package com.sofajohnlee.eunhyo2.feature.english

object EnglishWordCatalog {
    private val words = listOf(
        "ask", "book", "come", "day", "egg", "fine", "go", "hi", "it", "jump", "kick", "look", "make",
        "name", "open", "play", "queen", "run", "see", "time", "use", "very", "want", "xray", "yes", "zoo",
    )

    fun all(): List<String> = words

    fun display(word: String, capitalized: Boolean): String =
        if (capitalized) word.replaceFirstChar { it.uppercase() } else word.lowercase()
}
