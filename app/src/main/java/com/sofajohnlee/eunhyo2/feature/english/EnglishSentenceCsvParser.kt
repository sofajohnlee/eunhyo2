package com.sofajohnlee.eunhyo2.feature.english

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class EnglishSentenceCsvParser {
    fun parse(input: InputStream): List<EnglishSentenceEntry> =
        BufferedReader(InputStreamReader(input)).useLines { lines ->
            lines.mapNotNull(::parseLine).toList()
        }

    private fun parseLine(line: String): EnglishSentenceEntry? {
        if (line.isBlank()) return null
        val columns = splitCsv(line)
        if (columns.size < 4) return null
        return EnglishSentenceEntry(
            sentence = columns[1].trim(),
            meaning = columns[2].trim(),
            note = columns[3].trim(),
        )
    }

    private fun splitCsv(line: String): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        var quoted = false
        var i = 0
        while (i < line.length) {
            val c = line[i]
            when {
                c == '"' && quoted && i + 1 < line.length && line[i + 1] == '"' -> {
                    current.append('"')
                    i++
                }
                c == '"' -> quoted = !quoted
                c == ',' && !quoted -> {
                    result += current.toString()
                    current.clear()
                }
                else -> current.append(c)
            }
            i++
        }
        result += current.toString()
        return result
    }
}
