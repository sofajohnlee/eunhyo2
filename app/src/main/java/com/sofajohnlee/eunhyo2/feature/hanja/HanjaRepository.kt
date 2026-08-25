package com.sofajohnlee.eunhyo2.feature.hanja

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Hanja data source for the modernized app.
 *
 * The legacy app referenced CSV files by absolute paths under /storage/.../Download,
 * which are not part of the repository and are not valid scoped-storage behavior.
 * Eunhyo2 therefore provides a safe built-in starter set and supports importing
 * compatible CSV data through a caller-supplied InputStream (e.g. Storage Access Framework).
 */
class HanjaRepository {
    fun builtIn(): List<HanjaEntry> = listOf(
        HanjaEntry("人", "인", "사람"),
        HanjaEntry("大", "대", "큰"),
        HanjaEntry("小", "소", "작은"),
        HanjaEntry("山", "산", "메"),
        HanjaEntry("川", "천", "내"),
        HanjaEntry("日", "일", "날"),
        HanjaEntry("月", "월", "달"),
        HanjaEntry("火", "화", "불"),
        HanjaEntry("水", "수", "물"),
        HanjaEntry("木", "목", "나무"),
        HanjaEntry("金", "금", "쇠"),
        HanjaEntry("土", "토", "흙"),
        HanjaEntry("天", "천", "하늘"),
        HanjaEntry("地", "지", "땅"),
        HanjaEntry("學", "학", "배울"),
        HanjaEntry("校", "교", "학교"),
    )

    fun parseCsv(input: InputStream): List<HanjaEntry> =
        BufferedReader(InputStreamReader(input, Charsets.UTF_8)).useLines { lines ->
            lines.mapNotNull(::parseLine).toList()
        }

    private fun parseLine(line: String): HanjaEntry? {
        if (line.isBlank()) return null
        val columns = splitCsv(line)
        if (columns.size < 3) return null
        val offset = if (columns.size >= 4) 1 else 0
        val character = columns.getOrNull(offset)?.trim().orEmpty()
        val reading = columns.getOrNull(offset + 1)?.trim().orEmpty()
        val meaning = columns.getOrNull(offset + 2)?.trim().orEmpty()
        if (character.isBlank()) return null
        return HanjaEntry(character, reading, meaning)
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
