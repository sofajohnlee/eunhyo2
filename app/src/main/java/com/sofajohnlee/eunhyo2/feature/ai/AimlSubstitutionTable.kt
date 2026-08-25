package com.sofajohnlee.eunhyo2.feature.ai

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/** Parses Program AB-style two-column substitution files such as normal.txt/person.txt. */
class AimlSubstitutionTable private constructor(
    private val substitutions: List<Pair<String, String>>,
) {
    fun apply(value: String): String {
        var result = " $value "
        for ((from, to) in substitutions) {
            result = result.replace(from, to, ignoreCase = true)
        }
        return result.trim().replace(Regex("\\s+"), " ")
    }

    companion object {
        val EMPTY = AimlSubstitutionTable(emptyList())

        fun parse(input: InputStream): AimlSubstitutionTable =
            BufferedReader(InputStreamReader(input, Charsets.UTF_8)).useLines { lines ->
                AimlSubstitutionTable(lines.mapNotNull(::parseLine).toList())
            }

        private fun parseLine(line: String): Pair<String, String>? {
            val trimmed = line.trim()
            if (trimmed.isBlank() || trimmed.startsWith("#")) return null
            val fields = splitCsv(trimmed)
            if (fields.size < 2) return null
            val from = fields[0]
            val to = fields[1]
            if (from.isEmpty()) return null
            return from to to
        }

        private fun splitCsv(line: String): List<String> {
            val result = mutableListOf<String>()
            val current = StringBuilder()
            var quoted = false
            var index = 0
            while (index < line.length) {
                val c = line[index]
                when {
                    c == '"' && quoted && index + 1 < line.length && line[index + 1] == '"' -> {
                        current.append('"')
                        index++
                    }
                    c == '"' -> quoted = !quoted
                    c == ',' && !quoted -> {
                        result += current.toString()
                        current.clear()
                    }
                    else -> current.append(c)
                }
                index++
            }
            result += current.toString()
            return result
        }
    }
}
