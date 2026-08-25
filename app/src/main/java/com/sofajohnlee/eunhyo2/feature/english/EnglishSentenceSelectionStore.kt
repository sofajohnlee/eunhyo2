package com.sofajohnlee.eunhyo2.feature.english

import android.content.Context

class EnglishSentenceSelectionStore(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("english_sentence_selection", Context.MODE_PRIVATE)

    fun save(entries: List<EnglishSentenceEntry>) {
        val encoded = entries.joinToString("\n") {
            listOf(it.sentence, it.meaning, it.note).joinToString("\t") { value -> value.replace("\t", " ").replace("\n", " ") }
        }
        prefs.edit().putString(KEY_ENTRIES, encoded).apply()
    }

    fun load(): List<EnglishSentenceEntry> = prefs.getString(KEY_ENTRIES, null)
        ?.lineSequence()
        ?.mapNotNull { line ->
            val parts = line.split('\t')
            if (parts.size < 3) null else EnglishSentenceEntry(parts[0], parts[1], parts[2])
        }
        ?.toList()
        .orEmpty()

    companion object {
        private const val KEY_ENTRIES = "entries"
    }
}
