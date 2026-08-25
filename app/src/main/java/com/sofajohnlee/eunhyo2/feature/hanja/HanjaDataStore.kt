package com.sofajohnlee.eunhyo2.feature.hanja

import android.content.Context

class HanjaDataStore(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("hanja_data", Context.MODE_PRIVATE)

    fun load(): List<HanjaEntry> {
        val raw = prefs.getString(KEY_ENTRIES, null).orEmpty()
        if (raw.isBlank()) return emptyList()
        return raw.lineSequence().mapNotNull { line ->
            val parts = line.split('\t')
            if (parts.size < 3) null else HanjaEntry(unescape(parts[0]), unescape(parts[1]), unescape(parts[2]))
        }.toList()
    }

    fun save(entries: List<HanjaEntry>) {
        val raw = entries.joinToString("\n") { entry ->
            listOf(entry.character, entry.reading, entry.meaning).joinToString("\t") { escape(it) }
        }
        prefs.edit().putString(KEY_ENTRIES, raw).apply()
    }

    fun clear() {
        prefs.edit().remove(KEY_ENTRIES).apply()
    }

    private fun escape(value: String): String = value
        .replace("\\", "\\\\")
        .replace("\t", "\\t")
        .replace("\n", "\\n")

    private fun unescape(value: String): String {
        val out = StringBuilder()
        var escaped = false
        value.forEach { ch ->
            if (escaped) {
                out.append(if (ch == 't') '\t' else if (ch == 'n') '\n' else ch)
                escaped = false
            } else if (ch == '\\') {
                escaped = true
            } else {
                out.append(ch)
            }
        }
        if (escaped) out.append('\\')
        return out.toString()
    }

    companion object {
        private const val KEY_ENTRIES = "entries"
    }
}
