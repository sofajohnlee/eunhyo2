package com.sofajohnlee.eunhyo2.feature.ai

/** Minimal AIML pattern matcher supporting exact tokens plus * and _ wildcards. */
object AimlPatternMatcher {
    data class Match(val stars: List<String>)

    fun match(pattern: String, input: String): Match? {
        val tokens = pattern.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
        val words = input.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
        return matchFrom(tokens, words, 0, 0, mutableListOf())
    }

    private fun matchFrom(
        tokens: List<String>,
        words: List<String>,
        tokenIndex: Int,
        wordIndex: Int,
        stars: MutableList<String>,
    ): Match? {
        if (tokenIndex == tokens.size) {
            return if (wordIndex == words.size) Match(stars.toList()) else null
        }

        val token = tokens[tokenIndex]
        if (token != "*" && token != "_") {
            if (wordIndex >= words.size || !token.equals(words[wordIndex], ignoreCase = true)) return null
            return matchFrom(tokens, words, tokenIndex + 1, wordIndex + 1, stars)
        }

        val minimum = if (token == "_") 1 else 0
        for (end in words.size downTo wordIndex + minimum) {
            val capture = words.subList(wordIndex, end).joinToString(" ")
            stars += capture
            val matched = matchFrom(tokens, words, tokenIndex + 1, end, stars)
            if (matched != null) return matched
            stars.removeAt(stars.lastIndex)
        }
        return null
    }
}
